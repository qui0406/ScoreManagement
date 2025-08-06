/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.ClassResponse;

import java.util.List;

/**
 *
 * @author admin
 */
public interface ClassroomDetailsService {
    void create(CreateClassDetailsRequest request);
    void delete(String classDetailsId);

    List<ClassResponse> getClassroomsByTeacherId(String teacherId);

    String getTeacherIdByClassDetailId(String classDetailId);

    ClassDetailsResponse getClassDetailsById(String classDetailsId, String studentId);

    List<ClassDetailsResponse> getClassroomByStudentId(String studentId);

}