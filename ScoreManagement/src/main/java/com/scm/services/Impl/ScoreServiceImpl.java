/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.scm.dto.ScoreByTypeDTO;
import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ListScoreStudentRequest;
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
import java.math.BigDecimal;
import java.util.*;
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
    private ScoreRepository scoreRepo;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private CSVScoreMapper csvScoreMapper;


    private final int SCORE_TYPE_MIDDLE_TEST = 1;
    private final int SCORE_TYPE_FINAL_TEST = 2;

    @Override
    public void addOrUpdateScore(ScoreRequest scoreRequest, String teacherId) {
        scoreRequest.setTeacherId(Integer.parseInt(teacherId));

        Integer scoreTypeId = scoreRequest.getScoreTypeId();
        Integer studentId = scoreRequest.getStudentId();
        Integer classSubjectId = scoreRequest.getClassSubjectId();
        Integer scoreId = scoreRequest.getId();

        boolean isUpdate = scoreId != null;

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
        List<Score> grades = this.scoreRepo.getScoresByClassSubjectId(classSubjectId);
        List<ScoreResponse> responses = new ArrayList<>();

        for (Score grade : grades) {
            grade.setClassSubject(grade.getClassSubject());
            ScoreResponse response = scoreMapper.toScoreResponse(grade);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void updateCloseScore(Integer teacherId, Integer classSubjectId) {
        this.scoreRepository.closeScore(teacherId, classSubjectId);
    }

//    @Override
//    @Transactional
//    public void importScores(List<CSVScoreRequest> scores) {
//        for (CSVScoreRequest dto : scores) {
//            // Validate score
//            if (dto.getScore() == null || dto.getScore().doubleValue() < 0 || dto.getScore().doubleValue() > 10) {
//                throw new IllegalArgumentException("Invalid score value for studentId: " + dto.getStudentId());
//            }
//            Score score = csvScoreMapper.toScore(dto);
//
//            scoreRepository.save(score);
//        }
//    }

    @Override
    public Map<Integer, ScoreByTypeDTO> getGroupedScores(String studentId, String classSubjectId) {
        List<Score> allScores = scoreRepo.findScoreByStudentIdAndClassSubjectId(
                Integer.parseInt(studentId),
                Integer.parseInt(classSubjectId)
        );

        Map<Integer, ScoreByTypeDTO> scoresMap = new HashMap<>();
        for (Score sc : allScores) {
            int typeId = sc.getScoreType().getId();
            float scoreValue = sc.getScore().floatValue();

            ScoreByTypeDTO dto = scoresMap.getOrDefault(typeId, new ScoreByTypeDTO(
                    typeId,
                    sc.getScoreType().getScoreTypeName(),
                    new ArrayList<>()
            ));

            // CHẶN giá trị bị lặp (nếu cùng điểm đã tồn tại)
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
            scoreRequest.setStudentId(Integer.parseInt(request.getStudentId()));
            scoreRequest.setClassSubjectId(Integer.parseInt(request.getClassSubjectId()));
            scoreRequest.setTeacherId(Integer.parseInt(teacherId));
            scoreRequest.setScoreTypeId(type);
            this.scoreRepository.addOrUpdateScore(scoreMapper.toGrade(scoreRequest), teacherId);
        }
    }

    @Override
    public void addListScoreAllStudents(List<ListScoreStudentRequest> requests, String teacherId) {
        if (requests == null || requests.isEmpty()) {
           // throw new AppException(ErrorCode.INVALID_REQUEST, "Danh sách sinh viên rỗng");
        }
        for (ListScoreStudentRequest request : requests) {
            log.info("Student ID: " + request.getStudentId());
            log.info("ClassSubject ID: " + request.getClassSubjectId());
            for (Map.Entry<Integer, BigDecimal> entry : request.getScores().entrySet()) {
                log.info("Score Type: " + entry.getKey() + ", Score: " + entry.getValue());
            }
        }


        for (ListScoreStudentRequest studentRequest : requests) {
            this.addListScore(studentRequest, teacherId);
        }
    }
}