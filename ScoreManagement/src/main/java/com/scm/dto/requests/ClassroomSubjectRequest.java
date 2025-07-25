package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSubjectRequest {
    private Integer classroomId;
    private Integer subjectId;
    private Integer semesterId;
    private Integer teacherId;
    private Integer studentId;
}
