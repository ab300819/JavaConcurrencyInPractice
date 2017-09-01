package com.exercise.demo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by lenovo on 2017/8/17.
 */
//@WebListener
public class TrySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("SessionListener创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("SessionListener销毁");

    }
}
