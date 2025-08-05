package com.scm.mapper.decorator;

import com.scm.dto.StudentMessageDTO;
import com.scm.dto.TeacherMessageDTO;
import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.mapper.ConversationMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Conversation;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapperDecorator implements ConversationMapper {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Override
    public Conversation toConversation(ConversationRequest request) {
        Conversation conversation = new Conversation();
        conversation.setStudent(studentRepository.findById(request.getStudentId()));
        conversation.setTeacher(teacherRepository.findById(request.getTeacherId()));
        conversation.setClassDetails(classDetailsRepository.findById(request.getClassDetailId()));
        return conversation;
    }

    @Override
    public ConversationResponse toConversationResponse(Conversation request) {
        ConversationResponse response = new ConversationResponse();
        response.setId(request.getId().toString());
        response.setTeacherId(new TeacherMessageDTO(request.getTeacher().getUsername()));
        response.setStudentId(new StudentMessageDTO(request.getStudent().getUsername()));
        response.setCreatedAt(request.getCreatedAt());
        return response;
    }
}
