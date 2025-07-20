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
    int countStudentsInClassSubject(Integer classSubjectId);
    int countScoreTypesInClassSubject(Integer classSubjectId);
    ClassroomSubject findClassroomSubjectById(Integer classSubjectId);

    ClassroomSubject create(ClassroomSubject classroomSubject);
    void delete(Integer classSubjectId, String userId);

    boolean existClassSubjectRegister(ClassroomSubject classroomSubject);
}