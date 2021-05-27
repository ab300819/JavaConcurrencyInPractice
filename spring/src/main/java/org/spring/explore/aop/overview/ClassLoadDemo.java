package org.spring.explore.aop.overview;

import org.slf4j.Logger;

public class ClassLoadDemo {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClassLoadDemo.class);

    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        LOGGER.info(contextClassLoader.toString());
    }

}
