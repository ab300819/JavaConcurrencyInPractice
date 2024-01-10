package com.common.util.saerch;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

import com.alibaba.fastjson.JSONObject;

/**
 * @author boreas
 */
public interface SearchResultFactory {

    /**
     * 根据搜索接口返回，构建搜索结果
     *
     * @param json     搜索接口返回的 json 数据
     * @param type     原始文档类型
     * @param bodyName body 名称
     * @param mapper   结果集映射方法
     * @param <T>      原始文档类型
     * @param <N>      目标结果类型
     * @return 解析后的搜索结果
     */
    <T extends Doc, N extends Serializable> SearchResult<N> createResult(@Nonnull JSONObject json, Class<T> type, String bodyName,
                                                                         Function<List<T>, List<N>> mapper);

    /**
     * 构建空的搜索结果
     *
     * @param <N> 目标结果类型
     * @return 空的搜索结果
     */
    <N extends Serializable> SearchResult<N> createEmptyResult();

}
