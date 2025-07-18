/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;

import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreService {
    void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId);
    List<ScoreResponse> getScoresByClassSubjectId(Integer classSubjectId);
}