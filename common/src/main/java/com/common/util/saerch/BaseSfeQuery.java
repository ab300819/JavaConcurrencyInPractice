package com.common.util.saerch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import jdk.internal.joptsimple.internal.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 搜索组基础 query。该类封装了用于请求搜索组接口的一些通用行为，并提供以下特性
 * <ul>
 * <ol>支持翻页参数校验</ol>
 * <ol>支持带缓存和不带缓存请求</ol>
 * <ol>支持结果集映射，将接口返回映射为指定对象，如果指定需要缓存，可以根据不同映射分别进行缓存</ol>
 * <ol>支持诊断模式。诊断模式不需要业务层做任何额外的操作，通过诊断模式可以方便的获取搜索接口的信息。</ol>
 * </ul>
 *
 * @author boreas
 * @see #query()
 * @see #query(DocMapper)
 * @see #queryWithoutCache()
 * @see #queryWithoutCache(DocMapper)
 * @see SfeDiagnoseSupport
 */
@NotThreadSafe
@Setter
@Accessors(chain = true)
public abstract class BaseSfeQuery<T extends Doc> implements Serializable {

    private static final long serialVersionUID = 4649708809445948424L;
    /**
     * 搜索接口地址
     */
    public static final String SEARCH_API = "http://sfe.fe.host.dxy:88/dxysearch/aggregator";

    /**
     * 接口的默认 start 值
     *
     * @see #start
     */
    private static final int DEFAULT_START = 0;
    /**
     * 接口的默认 rows 值
     *
     * @see #rows
     */
    private static final int DEFAULT_ROWS = 10;
    /**
     * 接口的 start 值上限，超过 {@value} 时接口框架会返回错误
     *
     * @see #start
     */
    private static final int MAX_START = 200;
    /**
     * 接口的 rows 值上限，超过 {@value} 时接口框架会返回错误
     * #rows
     */
    private static final int MAX_ROWS = 100;
    /**
     * 当不需要 query 时，query 字段默认的值
     *
     * @see #query
     */
    private static final String NO_QUERY_PLACEHOLDER = "*:*";
    /**
     * 默认映射行为，返回原值
     */
    private final DefaultDocMapper<T, T> DEFAULT_MAPPER = new DefaultDocMapper<>(Strings.EMPTY, Function.identity());
    /**
     * 搜索结果工厂
     */
    @Setter(AccessLevel.PROTECTED)
    private cn.biomart.search.support.query.SearchResultFactory searchResultFactory = cn.biomart.search.support.query.SimpleSearchResultFactory.INSTANCE;
    /**
     * 当前请求
     */
    final HttpServletRequest request;
    /**
     * 搜索业务类型。所有搜索接口使用同一个接口地址，通过 searchType 区分具体业务（即 core, from 参数）
     */
    @Getter(AccessLevel.PROTECTED)
    private cn.biomart.search.support.query.SearchType searchType;
    /**
     * 用户 IP。理论上要求必填，但不填写也可调用。因此不做强校验（如 {@link com.dxy.commons.utils.IpUtil#is(String)}）
     */
    final String ip;
    /**
     * 是否是诊断模式。当请求用户是管理员，并且请求参数中带有 diagnoseMode 时进入诊断模式
     */
    private final boolean diagnoseMode;
    /**
     * 关键词
     */
    private String query = NO_QUERY_PLACEHOLDER;

    /**
     * 结果偏移量
     *
     * @see #DEFAULT_START
     * @see #MAX_START
     */
    private int start = 0;
    /**
     * 结果集大小
     *
     * @see #DEFAULT_START
     * @see #MAX_ROWS
     */
    private int rows = 10;
    /**
     * 是否启用参数合理化，默认是
     */
    private boolean normalizeParameters = true;
    /**
     * 是否启用缓存，默认为 <code>true</code>
     */
    boolean useCache = true;
    /**
     * 缓存块名，未指定的话将由{@link #buildCacheName(Map)}生成
     */
    private String cache = null;
    /**
     * 缓存 key，未指定的话将由{@link #buildCacheKey(Map, DocMapper)}生成
     */
    private String key = null;
    /**
     * 返回结果是否需要高亮。注意设置为 true 不一定有用，必须搜索接口本身支持高亮
     */
    private Boolean highlight;

    protected BaseSfeQuery(HttpServletRequest request, cn.biomart.search.support.query.SearchType searchType) {
        this.request = request;
        this.ip = WebUtil.getRemoteAddress(request);
        this.searchType = searchType;
        if (request != null) {
            SysUser user = UserVoParseUtil.getUser(request);
            this.diagnoseMode = user != null && WebUtil.getAsBoolean(request, "diagnoseMode")
                                && (user.isAdmin() || UserSessionInterceptor.isAdminProxy(request));
        } else {
            this.diagnoseMode = false;
        }
    }

    /**
     * 设置分页信息
     *
     * @param pageNumber 页码
     */
    public BaseSfeQuery<T> setPage(int pageNumber) {
        return setPage(pageNumber, DEFAULT_ROWS);
    }

    /**
     * 设置分页信息
     *
     * @param pageNumber 页码
     * @param pageSize   每页条数
     */
    public BaseSfeQuery<T> setPage(int pageNumber, int pageSize) {
        this.start = (pageNumber - 1) * pageSize;
        this.rows = pageSize;
        return this;
    }

    /**
     * 执行请求并返回结果
     *
     * @return 查询结果，接口超时
     */
    @Cached(minutes = 30)
    public cn.biomart.search.support.query.SearchResult<T> query() {
        return query(DEFAULT_MAPPER);
    }

    /**
     * 执行请求并返回结果
     *
     * @return 查询结果，接口超时
     */
    @Cached(minutes = 30)
    public <N extends Serializable> cn.biomart.search.support.query.SearchResult<N> query(DocMapper<T, N> mapper) {
        if (diagnoseMode) {
            return queryWithoutCache(mapper);
        }
        Map<String, Object> parameters = buildParametersForRequest();
        return MemcachedCacheManager.getOrGenerate(
                buildCacheName(parameters),
                buildCacheKey(parameters, mapper),
                key -> query(parameters, mapper, true), 1800);
    }

    /**
     * 执行请求并返回无缓存结果
     *
     * @return 查询结果，接口超时
     */
    public cn.biomart.search.support.query.SearchResult<T> queryWithoutCache() {
        return queryWithoutCache(DEFAULT_MAPPER);
    }

    /**
     * 执行请求并返回无缓存结果
     *
     * @return 查询结果，接口超时
     */
    public <N extends Serializable> cn.biomart.search.support.query.SearchResult<N> queryWithoutCache(DocMapper<T, N> mapper) {
        Map<String, Object> parameters = buildParametersForRequest();
        return query(parameters, mapper, false);
    }

    /**
     * 执行请求
     *
     * @param parameters  构造完成的参数
     * @param mapper      结果映射
     * @param ignoreCache 是否在缓存框架中使用，为 true 时当接口异常时会抛出{@link DoNotCacheException}，为 false 时返回空结果集
     */
    private <N extends Serializable> cn.biomart.search.support.query.SearchResult<N> query(Map<String, Object> parameters, DocMapper<T, N> mapper, boolean ignoreCache) {
        long start = System.currentTimeMillis();
        JSONObject jsonObject = HttpClientUtils.getJsonResult(SEARCH_API, parameters);
        long cost = System.currentTimeMillis() - start;
        log(jsonObject);
        if (diagnoseMode) {
            SfeDiagnoseSupport.diagnose(this, jsonObject, parameters, cost, mapper);
        }
        if (jsonObject == null) {
            if (ignoreCache) {
                throw new DoNotCacheException(searchResultFactory.createEmptyResult());
            } else {
                return searchResultFactory.createEmptyResult();
            }
        }
        return searchResultFactory.createResult(jsonObject, getDocType(), getBodyName(), mapper.getMapper());
    }

    /**
     * 构造请求参数
     */
    private Map<String, Object> buildParametersForRequest() {
        if (normalizeParameters) {
            normalizeParameters();
        }
        Map<String, Object> parameters = new HashMap<>(10);
        parameters.put("core", searchType.core);
        parameters.put("from", searchType.from);
        parameters.put("q", query);
        parameters.put("clientip", ip);
        parameters.put("start", start);
        parameters.put("rows", rows);
        if (disableCacheForDev() && Constants.DEV) {
            // 为获取线上真实情况，即使进入诊断模式，也
            //useCache = false;
        }
        parameters.put("sfe_cache", useCache ? 1 : 0);
        appendIfNotNull(parameters, "requestHandler", searchType.requestHandler);
        appendIfNotNull(parameters, "hl", highlight);

        Map<String, Object> appended = toParameters();
        if (MapUtils.isNotEmpty(appended)) {
            parameters.putAll(toParameters());
        }
        return parameters;
    }

    /**
     * 参数规范化
     */
    private void normalizeParameters() {
        // 翻页信息规范化
        if (start > MAX_START || start < 0) {
            start = DEFAULT_START;
        }
        if (rows > MAX_ROWS || rows <= 0) {
            rows = DEFAULT_ROWS;
        }
    }

    /**
     * 扩展参数。子类通过实现本方法，将自身属性转换为搜索接口参数
     */
    protected Map<String, Object> toParameters() {
        return null;
    }

    /**
     * 设置包含查询结果的结构 name
     *
     * @return 包含查询结果的结构 name
     */
    @Nonnull
    protected abstract String getBodyName();

    /**
     * 设置返回结果类型
     *
     * @return 返回结果类型
     */
    @Nonnull
    protected abstract Class<T> getDocType();

    protected String buildCacheName(Map<String, Object> params) {
        return StringUtils.defaultString(cache, getClass().getName());
    }

    protected <N extends Serializable> String buildCacheKey(Map<String, Object> params, DocMapper<T, N> mapper) {
        if (StringUtils.isNotBlank(key)) {
            return key;
        }
        TreeMap<String, Object> map = new TreeMap<>(params);
        map.remove("clientip");
        map.put("mapper", StringUtils.defaultString(mapper.getName()));
        return String.valueOf(map.hashCode());
    }

    /**
     * @return 测试环境是否禁用缓存，默认为 true。
     */
    protected boolean disableCacheForDev() {
        return true;
    }

    /**
     * 子类可以重写本方法，对搜索组返回结果记录日志，默认不记录
     *
     * @param result 搜索组接口返回
     */
    protected void log(JSONObject result) {
    }

    protected void appendIfNotNull(Map<String, Object> parameters, String key, Object value) {
        if (value == null) {
            return;
        }
        if (!(value instanceof String)) {
            parameters.put(key, value);
            return;
        }
        if (StringUtils.isNotEmpty((String) value)) {
            parameters.put(key, value);
        }
    }

    protected <U, V> void appendIfNotNull(Map<String, Object> parameters, String key, U value, Function<U, V> valueProcessor) {
        if (value == null) {
            return;
        }
        appendIfNotNull(parameters, key, valueProcessor.apply(value));
    }

    /**
     * 将搜索组接口的高亮标签替换为新的标签
     *
     * @param content 高亮文本
     * @return 替换后的高亮文本
     */
    public static String convertSfeHighlight(String content) {
        return convertHighlight(content, "em", "b");
    }

    /**
     * 将高亮标签替换为新的标签。本方法假设输入的标签是合法的
     *
     * @param content 高亮文本
     * @param oldTag  原高亮标签
     * @param newTag  新高亮标签
     * @return 替换后的高亮文本
     */
    public static String convertHighlight(String content, String oldTag, String newTag) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        return content.replaceAll(
                        "<" + Pattern.quote(oldTag) + "[^>]*>",
                        Matcher.quoteReplacement("<" + newTag + ">"))
                .replace("</" + oldTag + ">", "</" + newTag + ">");
    }
}
