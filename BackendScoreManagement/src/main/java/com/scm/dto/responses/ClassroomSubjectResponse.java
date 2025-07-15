/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class ClassroomSubjectResponse {
    private Integer classSubjectId;
    private String classroomName;
    private String subjectName;
    private String semester;
    private Integer totalStudents;
    private Integer gradeTypesCount;

    public Integer getClassSubjectId() {
        return classSubjectId;
    }

    public void setClassSubjectId(Integer classSubjectId) {
        this.classSubjectId = classSubjectId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getGradeTypesCount() {
        return gradeTypesCount;
    }

    public void setGradeTypesCount(Integer gradeTypesCount) {
        this.gradeTypesCount = gradeTypesCount;
    }
}
