/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreTypeService {
    List<ScoreTypeResponse> getScoreTypesByClassSubject(Integer classSubjectId);
    void addGradeTypeToClassSubject(ScoreTypeRequest scoreTypeRequest, Integer classSubjectId);
    void deleteGradeType(Integer id);
    boolean canAddMoreGradeTypes(Integer classSubjectId);
    void ensureMinimumGradeTypes(Integer classSubjectId);
}