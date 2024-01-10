package com.common.util.saerch;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author boreas
 */
public class SimpleSearchResultFactory implements SearchResultFactory {

    public static final SimpleSearchResultFactory INSTANCE = new SimpleSearchResultFactory();

    @Override
    public <T extends Doc, N extends Serializable> SearchResult<N> createResult(@Nonnull JSONObject json, Class<T> type,
                                                                                String bodyName, Function<List<T>, List<N>> mapper) {
        SimpleSearchResult<N> result = newInstance();
        JSONObject body = json.getJSONObject(bodyName);
        Optional<JSONArray> docsJsonArray = getDocJsonArray(body);
        setTotal(json, bodyName, result, body);
        // 允许外部对 result 结果进行修改，故不加 Collections.unmodifiableList
        List<T> docs = docsJsonArray
                .orElseGet(() -> new JSONArray(0)).stream()
                .map(o -> TypeUtils.castToJavaBean(o, type))
                .collect(Collectors.toList());
        processBeforeMap(result, docs, body);
        result.setDocs(mapper.apply(docs));
        return result;
    }

    protected <N extends Serializable> void setTotal(@Nonnull JSONObject json, String bodyName, SimpleSearchResult<N> result, JSONObject body) {
        Optional<JSONObject> response = Optional.ofNullable(body).map(b -> b.getJSONObject("response"));
        result.setTotal(response.map(b -> b.getIntValue("numFound")).orElse(0));
        result.setSfeTotal(Optional.ofNullable(json.getJSONObject(bodyName)).map(b -> b.getIntValue("numReturn")).orElse(0));
    }

    protected Optional<JSONArray> getDocJsonArray(JSONObject body) {
        Optional<JSONArray> docs = Optional.ofNullable(body)
                .map(b -> b.getJSONObject("response"))
                .map(r -> r.getJSONArray("docs"));
        return docs;
    }

    @Override
    public <N extends Serializable> SearchResult<N> createEmptyResult() {
        return newInstance().empty();
    }

    /**
     * 处理结果集映射前的逻辑
     *
     * @param result 搜索结果
     * @param docs   原始文档
     * @param body   原始搜索结果 json 中 body 部分
     * @param <T>    原始文档类型
     * @param <N>    目标结果类型
     */
    protected <T extends Doc, N extends Serializable> void processBeforeMap(SimpleSearchResult<N> result, List<T> docs,
                                                                            @Nullable JSONObject body) {
    }

    /**
     * 创建搜索结果实例
     *
     * @param <N> 搜索结果类型
     * @return 指定类型的搜索结果实例
     */
    protected <N extends Serializable> SimpleSearchResult<N> newInstance() {
        return new SimpleSearchResult<>();
    }
}
