package com.scm.mapper.decorator;

import com.scm.dto.StudentDTO;
import com.scm.dto.TeacherDTO;
import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumResponse;
import com.scm.mapper.EnrollDetailsMapper;
import com.scm.mapper.ForumMapper;
import com.scm.pojo.*;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ForumMapperDecorator implements ForumMapper {
    @Autowired
    @Qualifier("delegate")
    private ForumMapper delegate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Override
    public ForumResponse toResponse(Forum forum) {
        ForumResponse forumResponse = new ForumResponse();

        ClassDetails classDetails = classDetailsRepository.findById(forum.getClassDetails().getId().toString());

        forumResponse.setContent(forum.getContent());
        forumResponse.setCreatedAt(forum.getCreatedAt());
        forumResponse.setClassDetailId(forum.getClassDetails());
        User user = forum.getUser();

        if (user instanceof Student) {
            Student student = (Student) user;
            forumResponse.setStudentCreatedId(new StudentDTO(student.getId().toString(),student.getMssv(),
                    student.getLastName() + " " + student.getFirstName()));
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            forumResponse.setTeacherCreatedId(new TeacherDTO(teacher.getMsgv(),
                    teacher.getLastName() + " " + teacher.getFirstName()));
        }
        return forumResponse;
    }

    @Override
    public Forum toForum(ForumRequest request) {
        Forum forum = new Forum();

        ClassDetails classDetails = classDetailsRepository.findById(request.getClassDetailId());
        forum.setClassDetails(classDetails);

        User user = userRepository.findById(request.getUserCreatedId());
        forum.setUser(user);

        forum.setContent(request.getContent());
        forum.setCreatedAt(LocalDateTime.now());
        return forum;
    }
}
