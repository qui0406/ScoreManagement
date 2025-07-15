package com.scm.dto.requests;

import lombok.*;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeTypeRequest {
    private String gradeTypeName;

    public String getGradeTypeName() {
        return gradeTypeName;
    }

    public void setGradeTypeName(String gradeTypeName) {
        this.gradeTypeName = gradeTypeName;
    }
}
