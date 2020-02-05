package com.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * 定义事件
 *
 * @author mason
 */
public class UserRegisterEvent extends ApplicationEvent {

    public UserRegisterEvent(String source) {
        super(source);
    }
}
