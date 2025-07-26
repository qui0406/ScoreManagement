/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ClassSubjectScore;
import com.scm.pojo.ScoreType;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreTypeRepository {
    List<ScoreType> getScoreTypesByClassSubject(String classSubjectId);
    List<ScoreType> getDefaultScoreTypes();
    ScoreType findScoreTypeById(Integer id);
    void addOrUpdateGradeType(ScoreType scoreType);
    void deleteGradeType(Integer id);
    long countGradeTypesByClassSubject(Integer classSubjectId);

    List<ScoreType> getScoreTypes();

    void addScoreType(String classSubjectId, String scoreType);
    void deleteScoreType(String classSubjectId, String scoreTypeId);
    ScoreType getScoreTypeById(String scoreTypeId);
}