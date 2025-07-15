package com.scm.dto.responses;

import lombok.*;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeTypeResponse {
    private Integer id;
    private String gradeTypeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeTypeName() {
        return gradeTypeName;
    }

    public void setGradeTypeName(String gradeTypeName) {
        this.gradeTypeName = gradeTypeName;
    }
}
