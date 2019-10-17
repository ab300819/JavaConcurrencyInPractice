package com.netty.demo.codec;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

        assertThat(resultB, equalTo(resultA));
        assertThat(resultC, equalTo(resultA));

    }

}
