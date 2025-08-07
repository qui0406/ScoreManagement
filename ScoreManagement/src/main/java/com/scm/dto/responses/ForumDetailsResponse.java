package com.scm.dto.responses;

import com.scm.dto.StudentDTO;
import com.scm.dto.TeacherDTO;
import com.scm.pojo.Forum;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumDetailsResponse {
    private String forumId;
    private StudentDTO student;
    private TeacherDTO teacher;
    private String message;
    private LocalDateTime createdAt =  LocalDateTime.now();
}
