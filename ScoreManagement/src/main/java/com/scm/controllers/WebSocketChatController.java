package com.scm.controllers;

import com.scm.pojo.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("Received message: {}", chatMessage);
        if (chatMessage.getRoomId() == null || chatMessage.getReceiver() == null || chatMessage.getSender() == null) {
            log.error("Invalid message: roomId, receiver, or sender is null");
            return;
        }
        String destination = "/queue/" + chatMessage.getRoomId();
        log.info("Sending message to destination: /queue/{}", chatMessage.getRoomId());
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiver(), destination, chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getSender(), destination, chatMessage);
        log.info("Message sent to receiver: {} and sender: {}", chatMessage.getReceiver(), chatMessage.getSender());
    }
}