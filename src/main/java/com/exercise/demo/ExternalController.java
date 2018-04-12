package com.exercise.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lenovo on 2017/8/17.
 */

@Controller
public class ExternalController {

    private static Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @RequestMapping("/ws")
    @ResponseBody
    public Greeting serviceController(HelloMessage helloMessage) {

        return new Greeting("hello, " + helloMessage.getName() + "!");
    }
}
