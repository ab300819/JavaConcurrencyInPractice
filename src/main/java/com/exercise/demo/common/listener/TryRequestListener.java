package com.exercise.demo.common.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by lenovo on 2017/8/18.
 */

//@WebListener
public class TryRequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("RequestEvent销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("RequestEvent创建");
    }
}
