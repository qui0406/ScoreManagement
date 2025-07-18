/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.mapper.ScoreMapper;
import com.scm.pojo.*;
import com.scm.repositories.ScoreRepository;
import com.scm.services.ScoreService;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service
@Transactional
@Slf4j
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreRepository gradeRepo;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId) {
        scoreRequest.setTeacherId(Integer.parseInt(teacherId));
        Score score = scoreMapper.toGrade(scoreRequest);
        this.scoreRepository.addOrUpdateScore(score, teacherId);
    }

    @Override
    public List<ScoreResponse> getScoresByClassSubjectId(Integer classSubjectId) {
        List<Score> grades = this.gradeRepo.getScoresByClassSubjectId(classSubjectId);
        List<ScoreResponse> responses = new ArrayList<>();

        for (Score grade : grades) {
            grade.setClassSubject(grade.getClassSubject());
            ScoreResponse response = scoreMapper.toScoreResponse(grade);
            responses.add(response);
        }
        return responses;
    }
}