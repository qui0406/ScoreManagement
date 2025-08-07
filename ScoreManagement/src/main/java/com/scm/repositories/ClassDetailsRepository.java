/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ClassDetails;

import java.util.List;

/**
 *
 * @author admin
 */
public interface ClassDetailsRepository {
    List<ClassDetails> getClassroomSubjectsByTeacherId(String teacherId);

    int countStudent(ClassDetails classDetails);

    ClassDetails findById(String classSubjectId);

    void create(ClassDetails classDetails);
    void delete(String classDetailId);

    boolean existClassSubjectRegister(ClassDetails classDetails);

    ClassDetails getScoreSubjectByStudentId(String id, String classroomSubjectId, String teacherId, String semesterId);

    List<ClassDetails> getAllStudentsInClass(String classroomSubjectId, String teacherId, String semesterId);

    List<ClassDetails> getClassroomsByTeacherId(String teacherId);

    List<ClassDetails> getClassroomByStudentId(String studentId);
    List<ClassDetails> getAllClassDetails();
}