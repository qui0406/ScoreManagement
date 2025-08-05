package com.scm.mapper.decorator;

import com.scm.dto.StudentDTO;
import com.scm.dto.TeacherDTO;
import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.mapper.ForumDetailsMapper;
import com.scm.pojo.*;
import com.scm.repositories.ForumRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import com.scm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ForumDetailsMapperDecorator implements ForumDetailsMapper {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Override
    public ForumDetailsResponse toForumDetailsResponse(ForumDetails forumDetails) {
        ForumDetailsResponse response = new ForumDetailsResponse();

        response.setForum(forumDetails.getForum());
        response.setMessage(forumDetails.getMessage());
        response.setCreatedAt(forumDetails.getCreatedAt());

        User user = forumDetails.getUser();

        Student student = studentRepository.findById(user.getId().toString());
        if (student != null) {
            response.setStudent(new StudentDTO( student.getId().toString(),student.getMssv(),
                    student.getLastName() + " " + student.getFirstName()));
        }

        Teacher teacher = teacherRepository.findById(user.getId().toString());
        if (teacher != null) {
            response.setTeacher(new TeacherDTO(teacher.getMsgv(),
                    teacher.getLastName() + " " + teacher.getFirstName()));
        }

        return response;
    }

    @Override
    public ForumDetails toForum(ForumDetailsRequest request) {
        ForumDetails forumDetails = new ForumDetails();

        Forum forum = forumRepository.findById(request.getForumId());
        User user = userRepository.findById(request.getUserResponseId());

        forumDetails.setForum(forum);
        forumDetails.setUser(user);
        forumDetails.setMessage(request.getMessage());

        if (request.getCreatedAt() != null) {
            forumDetails.setCreatedAt(request.getCreatedAt());
        } else {
            forumDetails.setCreatedAt(LocalDateTime.now());
        }

        return forumDetails;
    }
}
