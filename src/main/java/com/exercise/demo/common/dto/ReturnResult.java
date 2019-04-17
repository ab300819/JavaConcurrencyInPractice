package com.exercise.demo.common.dto;

import lombok.Data;

/**
 * 返回结果公用
 *
 * @author mason
 */
@Data
public class ReturnResult {

    /**
     * 状态吗
     */
    public int code;

    /**
     * 信息
     */
    public String message;

    /**
     * 数据
     */
    public Object data;


}
