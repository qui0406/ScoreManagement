package com.scm.dto.responses;

import com.scm.dto.*;
import com.scm.pojo.Score;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreTableResponse {
    private StudentDTO student;
    private SubjectDTO subject;
    private ClassroomDTO classroom;
    private TeacherDTO teacher;
    private List<ScoreByTypeDTO> scores;
}
