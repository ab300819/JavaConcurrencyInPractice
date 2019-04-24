package com.exercise.demo.message.controller;

import com.exercise.demo.message.dto.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * 消息控制
 */
@Controller
public class MessageController {

    @MessageMapping("/send")
    // STOMP 协议中注解
    @SendToUser("/topic/replay")
    public MessageDto getAndSenMessage() throws Exception {
        MessageDto message = new MessageDto();

        return message;
    }


}
