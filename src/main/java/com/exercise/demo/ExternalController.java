package com.exercise.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by lenovo on 2017/8/17.
 */

@Controller
public class ExternalController {

    private static Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting serviceController(HelloMessage helloMessage) throws InterruptedException {
//        Thread.sleep(1000);

        return new Greeting("hello, " + helloMessage.getName() + "!");
    }
}
