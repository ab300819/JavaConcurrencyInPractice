package com.exercise.demo.message.controller;

import com.exercise.demo.common.util.RedisCacheUtil;
import com.exercise.demo.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 消息控制
 *
 * @author mason
 */
@Slf4j
@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @MessageMapping("/target")
    @SendTo("/topic/replay")
    public void sendToTarget(MessageDto messageDto) {
        template.convertAndSendToUser(messageDto.getTo(), "/topic/replay", messageDto);
    }

    @MessageMapping("/send")
    @SendTo("/topic/replay")
    public MessageDto sendToOriginal(MessageDto messageDto) {

        if (messageDto == null) {
            messageDto = new MessageDto();
            messageDto.setContent("空消息");
            return messageDto;
        }
        messageDto.setContent("消息：" + messageDto.getContent() + " 原路返回");
        return messageDto;
    }

}
