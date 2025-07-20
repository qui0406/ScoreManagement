/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author admin
 */
public interface ScoreService {
    void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId);
    List<ScoreResponse> getScoresByClassSubjectId(Integer classSubjectId);

    void updateCloseScore(Integer teacherId,  Integer classroomId);

    void importScores(List<CSVScoreRequest> scores);
}