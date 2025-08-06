package com.scm.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scm.dto.requests.ChatMessageRequest;
import com.scm.dto.responses.ChatMessageResponse;

import java.util.List;

public interface MessageService {
    List<ChatMessageResponse> getMessages(String conversationId, String userId);
    ChatMessageResponse create(ChatMessageRequest request, String conversationId, String userId) throws JsonProcessingException;
}
