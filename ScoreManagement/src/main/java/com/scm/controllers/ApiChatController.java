package com.scm.controllers;

import com.scm.pojo.Conversation;
import com.scm.pojo.Message;
import com.scm.services.ConversationService;
import com.scm.services.MessageService;
import com.scm.services.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.send")
    public void sendMessage(Message message, Principal principal) {
        // Xác thực người gửi
        Integer senderId = Integer.parseInt(principal.getName());

        // Lấy thông tin conversation
        Conversation conversation = conversationService.findById(message.getConversation().getId().toString());

        // Kiểm tra người gửi có trong conversation không
        if (!conversation.isParticipant(senderId)) {
            throw new SecurityException("You are not a participant in this conversation");
        }

//        // Thiết lập thông tin message
//        message.setSenderId(senderId.toString());
//        message.setCreatedAt(LocalDateTime.now());
//
//        // Lưu vào database
//        Message savedMessage = messageService.save(message);
//
//        // Lưu vào Redis
//        String redisKey = "conversation:" + conversation.getId() + ":messages";
//        redisTemplate.opsForList().rightPush(redisKey, savedMessage);
//
//        // Gửi qua RabbitMQ
//        amqpTemplate.convertAndSend(
//                "chat.exchange",
//                "conversation." + conversation.getId(),
//                savedMessage
//        );
//
//        // Gửi real-time đến người nhận
//        Integer receiverId = conversation.getOtherParticipantId(senderId);
//        messagingTemplate.convertAndSendToUser(
//                receiverId.toString(),
//                "/queue/messages",
//                savedMessage
//        );
//
//        // Gửi lại cho chính người gửi để đồng bộ UI
//        messagingTemplate.convertAndSendToUser(
//                senderId.toString(),
//                "/queue/messages",
//                savedMessage
//        );
//    }
//
//    @MessageMapping("/chat.conversation.{conversationId}")
//    public void getConversationMessages(@DestinationVariable Integer conversationId,
//                                        Principal principal) {
//        Integer userId = Integer.parseInt(principal.getName());
//
//        // Kiểm tra quyền truy cập
//        Conversation conversation = conversationService.findById(conversationId);
//        if (!conversation.isParticipant(userId)) {
//            throw new SecurityException("Access denied");
//        }
//
//        // Lấy tin nhắn từ Redis nếu có
//        String redisKey = "conversation:" + conversationId + ":messages";
//        List<Object> messages = redisTemplate.opsForList().range(redisKey, 0, -1);
//
//        if (messages == null || messages.isEmpty()) {
//            // Nếu không có trong Redis thì lấy từ database
//            messages = new ArrayList<>(conversationService.findById(conversationId));
//        }
//
//        // Gửi toàn bộ lịch sử chat về client
//        messagingTemplate.convertAndSendToUser(
//                userId.toString(),
//                "/queue/conversation/" + conversationId,
//                messages
//        );
    }

}
