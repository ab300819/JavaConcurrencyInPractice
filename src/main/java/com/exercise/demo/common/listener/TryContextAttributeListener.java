package com.exercise.demo.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * Created by lenovo on 2017/8/18.
 */
//@WebListener
public class TryContextAttributeListener implements ServletContextAttributeListener {

    private static Logger logger = LoggerFactory.getLogger(TryContextAttributeListener.class);

    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {

        logger.debug("添加对象：{}", scae.getName());

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {

        logger.debug("移除对象：{}", scae.getName());

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        logger.debug("替换对象：{}", scae.getName());

    }
}
