/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ScoreType;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreTypeRepository {
    List<ScoreType> getScoreTypesByClassDetails(String classSubjectId);
    ScoreType findById(String id);

    List<ScoreType> getScoreTypes();

    void addScoreType(String classDetailId, String scoreType);
    void deleteScoreType(String classDetailId, String scoreTypeId);
}