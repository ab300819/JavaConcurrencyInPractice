package com.exercise.demo.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by lenovo on 2017/8/17.
 */
//@WebListener
public class TryContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(TryContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextListener初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("ContextListener销毁");

    }
}
