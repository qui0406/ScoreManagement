/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ClassroomSubject;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ClassroomSubjectRepository {
    List<ClassroomSubject> getClassroomSubjectsByTeacherId(String teacherId);
    ClassroomSubject getClassroomSubjectById(Integer classSubjectId);
    int countStudentsInClassSubject(Integer classSubjectId);
    int countGradeTypesInClassSubject(Integer classSubjectId);
}
