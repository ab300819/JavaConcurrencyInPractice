package com.exercise.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.servlet.http.HttpServletRequest;

@Slf4j

@Configuration
@EnableWebSocketMessageBroker
public class StompOverWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //服务器发送给客户端的前缀
        config.enableSimpleBroker("/topic");
        //设置客户端发送给服务器的前缀
        config.setApplicationDestinationPrefixes("/app");

        config.setApplicationDestinationPrefixes("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册STOMP协议节点
        registry.addEndpoint("/stomp-over-websocket")
                // 添加握手处理
                .setHandshakeHandler((request, response, wsHandler, attributes) -> {

                    if (request instanceof HttpServletRequest) {
                        log.debug("request is httpServletRequest");
                    }
                    if (request instanceof ServletServerHttpRequest) {
                        log.debug("request is servletServerHttpRequest");
                    }

                    return false;
                });
    }
}
