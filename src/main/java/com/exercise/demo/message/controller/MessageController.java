package com.exercise.demo.message.controller;

import com.exercise.demo.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 消息控制
 */
@Slf4j
@Controller
public class MessageController {

    @MessageMapping("/send")
    // STOMP 协议中注解
    @SendToUser("/topic/replay")
    public MessageDto getAndSenMessage(MessageDto messageDto) throws Exception {
        return messageDto;
    }

}
