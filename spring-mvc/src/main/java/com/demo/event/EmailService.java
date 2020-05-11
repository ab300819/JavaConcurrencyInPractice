package com.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 事件订阅者
 *
 * @author mason
 */
@Slf4j
@Service
public class EmailService {

    @EventListener
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("{} 用户已注册，并发送邮件", event.getSource());
    }
}
