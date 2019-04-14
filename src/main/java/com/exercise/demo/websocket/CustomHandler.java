package com.exercise.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * 自定义 websocket 处理器
 * @author mason
 */
@Slf4j
@Component
public class CustomHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info("Received Message:" + message.getPayload());

        Thread.sleep(2000);
        TextMessage textMessage = new TextMessage("Hello, " + message.getPayload() + "!");
        session.sendMessage(textMessage);
    }
}
