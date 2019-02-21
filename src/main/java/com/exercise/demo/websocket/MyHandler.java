package com.exercise.demo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * Project: HelloSpring
 * Package: com.exercise.demo.websocket
 * Author: mason
 * E-mail: hotter2014@gmail.com
 * Time: 21:21
 * Date: 2017-09-07
 * Created with IntelliJ IDEA
 */

@Component
public class MyHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        logger.info("Received Message:" + message.getPayload());

        Thread.sleep(2000);
        TextMessage textMessage = new TextMessage("Hello, " + message.getPayload() + "!");
        session.sendMessage(textMessage);
    }
}
