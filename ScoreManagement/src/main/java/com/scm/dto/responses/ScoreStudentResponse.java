package com.scm.dto.responses;

import com.scm.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreStudentResponse {
    private StudentDTO student;
    private SubjectDTO subject;
    private ClassroomDTO classroom;
    private TeacherDTO teacher;
    private List<ScoreByTypeDTO> scores;
}
