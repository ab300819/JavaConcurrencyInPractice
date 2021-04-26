package com.common.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommonTest.class);

    @Test
    public void arrayListTest() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Object[] array = list.toArray();
        LOGGER.info("array class name {}", array.getClass().getSimpleName());
        array[0] = new Object();
    }

}
