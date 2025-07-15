/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.GradeRequest;
import com.scm.dto.responses.GradeResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface GradeService {
    void addOrUpdateGrade(GradeRequest gradeRequest, String teacherId);
    List<GradeResponse> getGradesByClassSubjectId(Integer classSubjectId);
}
