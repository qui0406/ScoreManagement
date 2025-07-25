/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ClassSubject;

import java.util.List;

/**
 *
 * @author admin
 */
public interface ClassroomSubjectRepository {
    List<ClassSubject> getClassroomSubjectsByTeacherId(String teacherId);
    int countStudentsInClassSubject(Integer classSubjectId);
    int countScoreTypesInClassSubject(Integer classSubjectId);
    ClassSubject findClassroomSubjectById(Integer classSubjectId);

    ClassSubject create(ClassSubject classSubject);
    void delete(Integer classSubjectId, String userId);

    boolean existClassSubjectRegister(ClassSubject classSubject);

    ClassSubject getScoreSubjectByStudentId(String id, String classroomSubjectId, String teacherId, String semesterId);

    List<ClassSubject> getAllStudentsInClass(String classroomSubjectId, String teacherId, String semesterId);
}