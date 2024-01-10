package com.common.util.saerch.experiment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.common.util.saerch.BaseSfeQuery;
import com.common.util.saerch.SearchType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 实验搜索
 *
 * @author boreas
 * @see <a href="https://wiki.dxy.net/x/4gQSBg">接口文档</a>
 */
@Setter
@Accessors(chain = true)
public class ExperimentQuery extends BaseSfeQuery<ExperimentDoc> {

    private static final long serialVersionUID = -6770269004931869634L;
    private static final String BODY_NAME = "experiment";
    private static final String SOURCE_PREFIX = "sourceid:";

    private Source source = Source.ALL;
    private List<Source> sources = Lists.newArrayList();
    /**
     * 数据公开状态 0 公开 1 不公开
     *
     * @see cn.biomart.experiment.common.enums.ExperimentQuestionAnswerState
     */
    private Integer state;

    private ExperimentQuery(HttpServletRequest request, SearchType searchType) {
        super(request, searchType);
        setHighlight(true);
    }

    public static ExperimentQuery from(HttpServletRequest request) {
        return new ExperimentQuery(request, SearchType.EXPERIMENT);
    }

    @Override
    protected Map<String, Object> toParameters() {
        Map<String, Object> extended = Maps.newHashMapWithExpectedSize(5);
        if (source != null && source.id != null) {
            extended.put("fq", SOURCE_PREFIX + source.id);
        }
        if (CollectionUtil.isNotEmpty(sources)) {
            StringBuilder sourceStr = new StringBuilder();
            sources.forEach(s -> sourceStr.append(s.id).append(" "));
            extended.put("fq", SOURCE_PREFIX + "(" + sourceStr + ")");
        }
        if (state != null) {
            extended.put("state_i-fqi", state);
        }
        return extended;
    }

    @Nonnull
    @Override
    protected String getBodyName() {
        return BODY_NAME;
    }

    @Nonnull
    @Override
    protected Class<ExperimentDoc> getDocType() {
        return ExperimentDoc.class;
    }

    /**
     * 搜索的数据来源
     */
    @AllArgsConstructor
    public enum Source {
        /**
         * 全部类型
         */
        ALL(null),
        /**
         * 实验 protocol （article_experiment, type in (0,1)）
         */
        PROTOCOL(0),
        /**
         * 资料（article_experiment, type in (2,3,4)）
         */
        DATA(1),
        /**
         * 实验
         */
        EXPERIMENT(2),
        /**
         * 实验问答
         */
        Q_AND_A(3);

        private static Map<Integer, Source> CACHE = Arrays.stream(Source.values())
                .collect(Collectors.toMap(v -> v.id, Function.identity()));

        private Integer id;

        public static Source of(Integer value) {
            return CACHE.getOrDefault(value, ALL);
        }

    }
}
