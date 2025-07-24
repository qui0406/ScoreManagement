package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreStudentRequest {
    private String studentId;
    private String teacherId;
    private String classSubjectId;
    private String semesterId;
}
