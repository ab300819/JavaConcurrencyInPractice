package com.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 用户注册服务（事件发布者）
 *
 * @author mason
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public void register(String name) {
        log.info("用户 {} 已注册", name);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
    }
}
