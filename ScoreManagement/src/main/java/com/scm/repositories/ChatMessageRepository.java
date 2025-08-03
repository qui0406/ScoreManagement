package com.scm.repositories;

import com.scm.pojo.ChatMessage;
import com.scm.pojo.User;

import java.util.List;

public interface ChatMessageRepository {
    List<ChatMessage> getChatMessagesHistory(User sender, User recipient);
    ChatMessage saveChatMessage(ChatMessage chatMessage);
}