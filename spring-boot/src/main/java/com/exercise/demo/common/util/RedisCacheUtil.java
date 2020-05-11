package com.exercise.demo.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 缓存工具类
 *
 * @author mason
 */
@Component
public class RedisCacheUtil {

    private final RedisTemplate<String, Serializable> cache;

    @Autowired
    public RedisCacheUtil(RedisTemplate<String, Serializable> cache) {
        this.cache = cache;
    }

    public  <T extends Serializable> T get(String key) {
        return (T) cache.opsForValue().get(key);
    }

    public  <T extends Serializable> void set(String key, T value) {
        cache.opsForValue().set(key, value);
    }

}
