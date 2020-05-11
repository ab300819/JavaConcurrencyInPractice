package com.netty.demo.codec;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 公共测试类
 *
 * @author mason
 */
public class CommonTest {

    @Test
    public void hashTest() {
        int hash = 11;
        int length = 8;
        int result = 3;

        int resultA = hash % length;
        int resultB = hash & (length - 1);
        int resultC = (length - 1) & hash;

        assertEquals(resultA, resultB);
        assertEquals(resultA, resultC);

    }

}
