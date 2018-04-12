package com.exercise.demo.websocket;

import com.exercise.demo.websocket.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by lenovo on 2017/8/29.
 */
//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(myHandle(), "/test");

    }

//    @Bean
    public WebSocketHandler myHandle() {

        return new MyHandler();
    }
}
