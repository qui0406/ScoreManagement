package com.scm.services;

import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.pojo.Conversation;

import java.util.List;

public interface ConversationService {
    void create(ConversationRequest request, String classDetailId, String studentId);
    void delete(String id, String userId);

    List<ConversationResponse> getAllConversationsByUserId(String userId);

    Conversation findById(String id);

    List<ConversationResponse> getAllConversationsByClassDetailId(String classId, String userId);
}
