package com.exercise.demo.common.util;

import com.exercise.demo.usercenter.dto.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存工具类
 *
 * @author mason
 */
public class RedisCacheUtil {

    public final static Map<String, User> CACHE = new HashMap<>();

    public static User get(String userToken) {

        return null;
    }

}
