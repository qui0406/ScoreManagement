/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.GradeTypeRequest;
import com.scm.dto.responses.GradeTypeResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface GradeTypeService {
    List<GradeTypeResponse> getGradeTypesByClassSubject(Integer classSubjectId);
    void addGradeTypeToClassSubject(GradeTypeRequest gradeTypeRequest, Integer classSubjectId);
    void deleteGradeType(Integer id);
    boolean canAddMoreGradeTypes(Integer classSubjectId);
    void ensureMinimumGradeTypes(Integer classSubjectId);
}
