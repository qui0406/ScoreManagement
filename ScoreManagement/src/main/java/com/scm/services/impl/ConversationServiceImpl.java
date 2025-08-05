package com.scm.services.impl;

import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ConversationMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Conversation;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ConversationRepository;
import com.scm.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public void create(ConversationRequest request, String classDetailId, String studentId) {
        String teacherId = classDetailsRepository.findById(classDetailId).getTeacher().getId().toString();
        request.setTeacherId(teacherId);
        request.setStudentId(studentId);
        request.setClassDetailId(classDetailId);
        this.conversationRepository.create(this.conversationMapper.toConversation(request));
    }

    @Override
    public void delete(String id, String userId) {
        Conversation conversation = this.conversationRepository.findById(id);
        if (!conversation.getTeacher().getId().toString().equals(userId) &&
                !conversation.getStudent().getId().toString().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        this.conversationRepository.delete(conversation);
    }

    @Override
    public List<ConversationResponse> getAllConversationsByUserId(String userId) {
        List<Conversation> conversations = this.conversationRepository.getAllConversationsByUserId(userId);
        List<ConversationResponse> conversationResponses = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationResponses.add(this.conversationMapper.toConversationResponse(conversation));
        }
        return conversationResponses;
    }

    @Override
    public List<ConversationResponse> getAllConversationsByClassDetailId(String classId, String userId) {
        ClassDetails classDetails =  this.classDetailsRepository.findById(classId);
        if(!classDetails.getTeacher().getId().toString().equals(userId)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        List<Conversation> conversations = this.conversationRepository.getConversationsInClass(classId);
        List<ConversationResponse> conversationResponses = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationResponses.add(this.conversationMapper.toConversationResponse(conversation));
        }
        return conversationResponses;
    }

    @Override
    public Conversation findById(String id) {
        return this.conversationRepository.findById(id);
    }
}
