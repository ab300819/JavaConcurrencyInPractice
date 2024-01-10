package com.common.util.saerch.experiment;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.common.util.saerch.BaseSfeQuery;
import com.common.util.saerch.SearchType;


public class ExperimentKeywordQuery extends BaseSfeQuery<ExperimentKeywordDoc> {


    protected ExperimentKeywordQuery(HttpServletRequest request, SearchType searchType) {
        super(request, searchType);
    }

    public static ExperimentKeywordQuery from(HttpServletRequest request, SearchType searchType) {
        switch (searchType) {
            case EXPERIMENT_KEYWORD:
                return new ExperimentKeywordQuery(request, searchType);
            default:
                throw new IllegalArgumentException("非法搜索类型");
        }
    }

    /**
     * 设置包含查询结果的结构 name
     *
     * @return 包含查询结果的结构 name
     */
    @Nonnull
    @Override
    protected String getBodyName() {
        return SearchType.EXPERIMENT_KEYWORD.getCore();
    }

    /**
     * 设置返回结果类型
     *
     * @return 返回结果类型
     */
    @Nonnull
    @Override
    protected Class<ExperimentKeywordDoc> getDocType() {
        return ExperimentKeywordDoc.class;
    }
}
