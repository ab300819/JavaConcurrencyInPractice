package com.demo.config;

import com.demo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class BeanConfig {
    @Bean
    @RequestScope
    public User createUser() {
        User user = new User();
        user.setTimeStamp(System.currentTimeMillis());
        return user;
    }
}
