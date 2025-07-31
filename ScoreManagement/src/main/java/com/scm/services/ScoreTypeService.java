/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.pojo.ScoreType;

import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreTypeService {
    List<ScoreTypeResponse> getScoreTypes();
    List<ScoreTypeResponse> getScoreTypesByClassDetails(String classDetailId);
    void addScoreType(String classDetailId, String scoreTypeId);
    void deleteScoreType(String classDetailId, String scoreTypeId);
    ScoreType getScoreTypeById(String id);
}