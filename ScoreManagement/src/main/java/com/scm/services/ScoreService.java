/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.ScoreByTypeDTO;
import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.pojo.Score;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author admin
 */
public interface ScoreService {
    void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId);

    void blockScore(String teacherId,  String classDetailId);

    Map<Integer, ScoreByTypeDTO> filterScoreByStudent(List<Score> scores);

    void addListScore(ListScoreStudentRequest request, String teacherId);

    void addListScoreAllStudents(List<ListScoreStudentRequest> request, String teacherId);

    boolean getStatusBlock(String classDetailId);
}