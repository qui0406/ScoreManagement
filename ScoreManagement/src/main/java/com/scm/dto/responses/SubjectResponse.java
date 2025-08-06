package com.scm.dto.responses;

import com.scm.pojo.ClassDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
    private String id;
    private String subjectName;
    private ClassDetails classDetails;
}
