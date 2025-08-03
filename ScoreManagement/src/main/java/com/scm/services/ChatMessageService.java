package com.scm.services;

import com.scm.pojo.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getChatMessagesHistory(String senderUsername, String recipientUsername);
    ChatMessage saveChatMessage(ChatMessage chatMessage);
}