package com.scm.repositories;

import com.scm.pojo.Conversation;

import java.util.List;

public interface ConversationRepository {
    void create(Conversation conversation);
    void delete(Conversation conversation);

    Conversation findById(String id);

    List<Conversation> getAllConversationsByUserId(String userId);
}
