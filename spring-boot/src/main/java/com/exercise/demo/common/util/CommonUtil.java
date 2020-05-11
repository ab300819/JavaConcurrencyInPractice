package com.exercise.demo.common.util;

import java.util.UUID;

/**
 * 通用工具类
 *
 * @author mason
 */
public class CommonUtil {

    /**
     * 生成 uuid 字符串
     *
     * @return uuid 字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
