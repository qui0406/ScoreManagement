/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.ScoreByTypeDTO;
import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.CSVScoreMapper;
import com.scm.mapper.ScoreMapper;
import com.scm.pojo.*;
import com.scm.repositories.ScoreRepository;
import com.scm.services.ScoreService;

import java.math.BigDecimal;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    private final String SCORE_TYPE_MIDDLE_TEST = "1";
    private final String SCORE_TYPE_FINAL_TEST = "2";

    @Override
    public void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId) {
        String scoreTypeId = scoreRequest.getScoreTypeId();
        String studentId = scoreRequest.getStudentId();
        String classDetail = scoreRequest.getClassDetailId();

        Score score = scoreRepository.getScoreByClassDetailIdAndStudentAndScoreType(classDetail, studentId, scoreTypeId);

        if (score != null) {
            if (scoreTypeId.equals(SCORE_TYPE_MIDDLE_TEST) || scoreTypeId.equals(SCORE_TYPE_FINAL_TEST)) {
                boolean alreadyHasTest = scoreRepository.checkTestExisted(classDetail, scoreTypeId, studentId);
                if (alreadyHasTest) {
                    throw new AppException(ErrorCode.ONLY_1_COLLUM);
                }
            } else {
                boolean canAddMore = scoreRepository.checkOver5TestExisted(classDetail, scoreTypeId, studentId);
                if (!canAddMore) {
                    throw new AppException(ErrorCode.OVER_5_TEST);
                }
            }
        }
        Score s = scoreMapper.toScore(scoreRequest);
        if (score != null) {
            score.setScore(s.getScore());
            scoreRepository.updateScore(score);
        } else {
            scoreRepository.addScore(s);
        }
    }

    @Override
    public List<ScoreResponse> getScoresByClassSubjectId(Integer classSubjectId) {
        List<Score> grades = this.scoreRepository.getScoresByClassSubjectId(classSubjectId);
        List<ScoreResponse> responses = new ArrayList<>();

        for (Score grade : grades) {
            grade.setClassDetails(grade.getClassDetails());
            ScoreResponse response = scoreMapper.toScoreResponse(grade);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void blockScore(String teacherId, String classDetailId) {
        this.scoreRepository.blockScore(classDetailId);
    }


    @Override
    public Map<Integer, ScoreByTypeDTO> getGroupedScores(List<Score> scores) {
        Map<Integer, ScoreByTypeDTO> scoresMap = new HashMap<>();
        for (Score sc : scores) {
            int typeId = sc.getScoreType().getId();
            float scoreValue = sc.getScore().floatValue();

            ScoreByTypeDTO dto = scoresMap.getOrDefault(typeId, new ScoreByTypeDTO(
                    typeId,
                    sc.getScoreType().getScoreTypeName(),
                    new ArrayList<>()
            ));

            if (!dto.getScores().contains(scoreValue)) {
                dto.getScores().add(BigDecimal.valueOf(scoreValue));
            }

            scoresMap.put(typeId, dto);
        }
        return scoresMap;
    }

    @Override
    public void addListScore(ListScoreStudentRequest request, String teacherId) {
        Map<Integer, BigDecimal> scoreMap = request.getScores();

        for (Map.Entry<Integer, BigDecimal> entry : scoreMap.entrySet()) {
            Integer type = entry.getKey();
            BigDecimal value = entry.getValue();

            ScoreRequest scoreRequest = new ScoreRequest();
            scoreRequest.setScore(value);
            scoreRequest.setStudentId(request.getStudentId());
            scoreRequest.setClassDetailId(request.getClassDetailId());
            scoreRequest.setScoreTypeId(type.toString());
            this.addOrUpdateScore(scoreRequest, teacherId);
        }
    }

    @Override
    public void addListScoreAllStudents(List<ListScoreStudentRequest> requests, String teacherId) {
        for (ListScoreStudentRequest studentRequest : requests) {
            this.addListScore(studentRequest, teacherId);
        }
    }

    @Override
    public boolean getStatusBlock(String classDetailId) {
        return this.scoreRepository.getStatusBlock(classDetailId);
    }
}