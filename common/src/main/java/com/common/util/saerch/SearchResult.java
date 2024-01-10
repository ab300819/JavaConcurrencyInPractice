package com.common.util.saerch;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author boreas
 */
public interface SearchResult<T extends Serializable> extends Serializable {

    /**
     * 获取文档列表
     *
     * @return 文档列表
     */
    List<T> getDocs();

    /**
     * 获取实际总数总数
     *
     * @return 实际查询到的条目总数
     */
    int getTotal();

    /**
     * 获取搜索接口可返回的总数
     *
     * @return 搜索接口可返回的总数
     */
    int getSfeTotal();

    /**
     * 将搜索结果文档集映射为其他类型的列表
     *
     * @param mapper 映射方法
     * @param <N>    目标类型
     * @return 映射后的列表
     */
    default <N> List<N> map(@Nonnull Function<T, N> mapper) {
        return getDocs() == null ? null : getDocs().stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 尝试转换搜索结果类型
     *
     * @param <N> 目标类型
     * @return 目标类型的搜索结果
     */
    @SuppressWarnings("unchecked")
    default <N extends SearchResult<T>> N cast(Class<N> type) {
        if (this.getClass() == type) {
            return (N) this;
        }
        return null;
    }

}
