package com.scm.services.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.dto.requests.ChatMessageRequest;
import com.scm.dto.responses.ChatMessageResponse;
import com.scm.pojo.Conversation;
import com.scm.pojo.Message;
import com.scm.pojo.WebSocketSession;
import com.scm.repositories.ConversationRepository;
import com.scm.repositories.MessageRepository;
import com.scm.repositories.WebSocketSessionRepository;
import com.scm.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private WebSocketSessionRepository webSocketSessionRepository;

    @Autowired
    private SocketIOServer socketIOServer;

    @Override
    public List<ChatMessageResponse> getMessages(String conversationId, String userId) {
        return List.of();
    }

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public ChatMessageResponse create(ChatMessageRequest request, String userId) throws JsonProcessingException {
        Message message = toChatMessage(request);
        this.messageRepository.create(message);

        String chatMessage = objectMapper.writeValueAsString(message.getMessage());

        Conversation conversation = conversationRepository.findById(request.getConversationId());

        String socketSessionStudent = conversation.getStudent().getId().toString();
        String socketSessionTeacher = conversation.getTeacher().getId().toString();

        List<WebSocketSession> listSocketSessions = new ArrayList<>();
        List<WebSocketSession> webSocketSessions = webSocketSessionRepository
                .findAllSocketSessionsById(socketSessionStudent);
        List<WebSocketSession> webSocketSessionsTeacher = webSocketSessionRepository
                .findAllSocketSessionsById(socketSessionTeacher);

        listSocketSessions.addAll(webSocketSessions);
        listSocketSessions.addAll(webSocketSessionsTeacher);


        Set<String> socketSessionIds = listSocketSessions.stream()
                .map(WebSocketSession::getId)
                .collect(Collectors.toSet());


        Set<String> websocketMe = webSocketSessionRepository
                .findAllSocketSessionsById(userId).stream().map(WebSocketSession::getId)
                .collect(Collectors.toSet());

        ChatMessageResponse chatMessageResponse = toChatMessageResponse(message);
        socketIOServer.getAllClients().forEach(client -> {
            if(socketSessionIds.contains(client.getSessionId().toString())) {
                String chatMessages = null;
                if(socketSessionIds.contains(websocketMe.toString())) {
                    try {
                        chatMessageResponse.setMe(true);
                        chatMessages = objectMapper.writeValueAsString(chatMessageResponse);
                        client.sendEvent(chatMessages);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                client.sendEvent("message", chatMessage);
            }
        });

        return toChatMessageResponse(message);
    }


    private ChatMessageResponse toChatMessageResponse(Message chatMessage) {
        ChatMessageResponse response = new ChatMessageResponse();

        response.setConversationId(conversationRepository.findById(chatMessage.getConversationId().toString()));
        response.setContent(chatMessage.getMessage());
        response.setCreatedDate(chatMessage.getCreatedDate());
        return response;
    }

    private Message toChatMessage(ChatMessageRequest request) {
        Message message = new Message();
        Conversation conversation = conversationRepository.findById(request.getConversationId());
        message.setMessage(request.getMessage());
        message.setConversationId(conversation);
        return message;
    }
}
