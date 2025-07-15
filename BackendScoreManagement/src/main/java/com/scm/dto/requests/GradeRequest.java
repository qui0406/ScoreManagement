/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.dto.requests;

import lombok.*;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {
    private Long studentId;
    private Integer classSubjectId;
    private Integer gradeTypeId;
    private BigDecimal score;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getClassSubjectId() {
        return classSubjectId;
    }

    public void setClassSubjectId(Integer classSubjectId) {
        this.classSubjectId = classSubjectId;
    }

    public Integer getGradeTypeId() {
        return gradeTypeId;
    }

    public void setGradeTypeId(Integer gradeTypeId) {
        this.gradeTypeId = gradeTypeId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
