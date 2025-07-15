/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.responses.ClassroomSubjectResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ClassroomSubjectService {
    List<ClassroomSubjectResponse> getClassroomSubjectsByTeacherId(String teacherId);
    ClassroomSubjectResponse getClassroomSubjectDetails(Integer classSubjectId);
}
