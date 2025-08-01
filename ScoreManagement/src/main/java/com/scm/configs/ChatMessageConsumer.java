package com.scm.configs;

import com.scm.pojo.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "public.chat.queue")
    public void receivePublicMessage(com.scm.pojo.Message message) {
        // Xử lý logic bổ sung nếu cần
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    @RabbitListener(queues = "private.chat.queue.#")
    public void receivePrivateMessage(Message message,
                                      @Header(required = false, name = "userId") String userId) {
        if (userId != null) {
            messagingTemplate.convertAndSendToUser(userId, "/queue/private", message);
        }
    }
}