package com.exercise.demo.websocket;

import com.exercise.demo.common.util.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 使用 stomp 协议
 *
 * @author mason
 * @since 2019-04-15
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class StompOverWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final RedisCacheUtil redisCacheUtil;

    @Autowired
    public StompOverWebSocketConfig(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //服务器发送给客户端的前缀
        config.enableSimpleBroker("/topic", "/user");
        //设置客户端发送给服务器的前缀
        config.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册STOMP协议节点
        registry.addEndpoint("/stomp-over-websocket")
                // 添加拦截器
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        log.debug("enter into interceptor");

                        if (request instanceof ServletServerHttpRequest) {
                            log.debug("request is ServletServerHttpRequest");
                            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
                            HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
                            HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();
                            String uid = httpServletRequest.getParameter("uid");
                            redisCacheUtil.set(uid, session.getId());
                            log.debug("session id is {}", session.getId());
                        }
                        return super.beforeHandshake(request, response, wsHandler, attributes);
                    }
                });
    }
}
