package com.common.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * id 生成器
 *
 * @author maosn
 */
public final class IdUtil {

    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * generate id
     *
     * @return
     */
    public static long nextId() {
        return IDX.incrementAndGet();
    }

}
