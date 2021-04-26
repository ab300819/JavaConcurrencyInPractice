package org.spring.explore.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    public static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void testLog(){
        log.info("id:{}-name:{}-class name:{}",Thread.currentThread().getId(),Thread.currentThread().getName(),Thread.currentThread().getClass().getName());
    }

}
