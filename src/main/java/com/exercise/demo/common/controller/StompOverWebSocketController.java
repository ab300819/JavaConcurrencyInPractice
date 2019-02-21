package com.exercise.demo.common.controller;

import com.exercise.demo.common.dto.Greeting;
import com.exercise.demo.common.dto.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class StompOverWebSocketController {

    @MessageMapping("/hello")
    @SendTo("/greetings")
    public Greeting getAndSend(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
