package com.common.util.saerch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author boreas
 */
@Data
public class SimpleSearchResult<T extends Serializable> implements SearchResult<T> {

    private static final long serialVersionUID = -1;

    private List<T> docs;
    /**
     * 实际总数
     */
    private int total;
    /**
     * sfe可以返回的总数
     */
    private int sfeTotal;

    protected <T extends Serializable> SearchResult<T> empty() {
        // 需要序列化，不将属性设置为 final
        SimpleSearchResult<T> empty = new SimpleSearchResult<>();
        empty.setDocs(new ArrayList<>());
        return empty;
    }
}
