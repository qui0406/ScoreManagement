package com.scm.dto.responses;

import com.scm.dto.StudentDTO;
import com.scm.dto.TeacherDTO;
import com.scm.pojo.ClassDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumResponse {
    private Integer id;
    private String classDetailId;
    private StudentDTO studentCreatedId;
    private TeacherDTO teacherCreatedId;
    private String content;
    private LocalDateTime createdAt= LocalDateTime.now();
}
