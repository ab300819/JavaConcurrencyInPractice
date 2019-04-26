package com.exercise.demo.message.controller;

import com.exercise.demo.common.util.CommonUtil;
import com.exercise.demo.common.util.RedisCacheUtil;
import com.exercise.demo.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @MessageMapping("/send")
//    @SendToUser("/topic/replay")
    public void getAndSenMessage(MessageDto messageDto) throws Exception {
        template.convertAndSendToUser(messageDto.getTo(),"/topic/replay",messageDto.getContent()+messageDto.getTo());
    }

}
