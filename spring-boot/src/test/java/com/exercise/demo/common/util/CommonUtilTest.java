package com.exercise.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class CommonUtilTest {

    @Test
    public void getUUID() {
        String uuid = CommonUtil.getUUID();
        assertNotNull(uuid);
        log.debug("generate uuid is {}", uuid);
    }

}