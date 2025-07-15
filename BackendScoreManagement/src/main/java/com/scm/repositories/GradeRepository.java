/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Grade;
import java.util.List;

/**
 *
 * @author admin
 */
public interface GradeRepository {
    void addOrUpdateGrade(Grade grade);
    List<Grade> getGradesByClassSubjectId(Integer classSubjectId);
    Grade getGradeByStudentAndClassSubjectAndType(Long studentId, Integer classSubjectId, Integer gradeTypeId);
}
