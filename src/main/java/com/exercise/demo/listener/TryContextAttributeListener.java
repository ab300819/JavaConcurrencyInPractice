package com.exercise.demo.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by lenovo on 2017/8/18.
 */
@WebListener
public class TryContextAttributeListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("添加对象：" + scae.getName());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("移除对象：" + scae.getName());

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("替换对象：" + scae.getName());

    }
}
