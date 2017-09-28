package com.exercise.demo.websocket;

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
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {


    }
}
