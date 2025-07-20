/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.helpers.CSVHelper;
import com.scm.mapper.CSVScoreMapper;
import com.scm.mapper.ScoreMapper;
import com.scm.pojo.*;
import com.scm.repositories.ScoreRepository;
import com.scm.services.ScoreService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private ScoreRepository gradeRepo;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private CSVScoreMapper  csvScoreMapper;


    private final int SCORE_TYPE_MIDDLE_TEST = 1;
    private final int SCORE_TYPE_FINAL_TEST = 2;

    @Override
    public void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId) {
        scoreRequest.setTeacherId(Integer.parseInt(teacherId));

        Integer scoreTypeId = scoreRequest.getScoreTypeId();
        Integer studentId = scoreRequest.getStudentId();
        Integer classSubjectId = scoreRequest.getClassSubjectId();
        Integer scoreId = scoreRequest.getId();

        boolean isUpdate = scoreId != null;  // <- sửa chỗ này

        if (!isUpdate) {
            if (scoreTypeId == SCORE_TYPE_MIDDLE_TEST || scoreTypeId == SCORE_TYPE_FINAL_TEST) {
                boolean check = scoreRepository.checkTestExsit(classSubjectId, scoreTypeId, studentId);
                if (!check) {
                    throw new AppException(ErrorCode.ONLY_1_COLLUM);
                }
            } else {
                boolean check = scoreRepository.checkOver5TestExsit(classSubjectId, scoreTypeId, studentId);
                if (!check) {
                    throw new AppException(ErrorCode.OVER_5_TEST);
                }
            }
        }

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

    @Override
    public void updateCloseScore(Integer teacherId, Integer classroomId) {
        this.scoreRepository.closeScore(teacherId, classroomId);
    }

    @Override
    @Transactional
    public void importScores(List<CSVScoreRequest> scores) {
        for (CSVScoreRequest dto : scores) {
            // Validate score
            if (dto.getScore() == null || dto.getScore().doubleValue() < 0 || dto.getScore().doubleValue() > 10) {
                throw new IllegalArgumentException("Invalid score value for studentId: " + dto.getStudentId());
            }
            Score score = csvScoreMapper.toScore(dto);

            scoreRepository.save(score);
        }
    }
}