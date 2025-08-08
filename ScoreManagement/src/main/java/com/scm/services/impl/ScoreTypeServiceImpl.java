/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ScoreTypeMapper;
import com.scm.pojo.ClassDetailsScoreType;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Teacher;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ScoreTypeRepository;
import com.scm.repositories.TeacherRepository;
import com.scm.services.ScoreTypeService;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
@Slf4j
public class ScoreTypeServiceImpl implements ScoreTypeService {
    @Autowired
    private ScoreTypeRepository scoreTypeRepo;

    @Autowired
    private ScoreTypeMapper scoreTypeMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<ScoreTypeResponse> getScoreTypes() {
        List<ScoreType> scoreTypes = this.scoreTypeRepo.getScoreTypes();

        List<ScoreTypeResponse> responses = new ArrayList<>();
        for (ScoreType scoreType : scoreTypes) {
            responses.add(scoreTypeMapper.scoreTypeResponse(scoreType));
        }
        return responses;
    }

    @Override
    public List<ScoreTypeResponse> getScoreTypesByClassDetails(String classDetailId) {
        List<ScoreType> scoreTypes = this.scoreTypeRepo.getScoreTypesByClassDetails(classDetailId);
        List<ScoreTypeResponse> responses = new ArrayList<>();

        for (ScoreType scoreType : scoreTypes) {
            responses.add(scoreTypeMapper.scoreTypeResponse(scoreType));
        }

        return responses;
    }

    @Override
    public void addScoreType(String classDetailId, ScoreTypeRequest request, String teacherId) {
        if(request.getScoreTypeId().equals("1") || request.getScoreTypeId().equals("2") ) {
            throw new AppException(ErrorCode.SCORE_TYPE_INCORRECT);
        }
        Teacher teacher = this.teacherRepository.getTeacherByClassDetailId(classDetailId);
        if(!teacher.getId().toString().equals(teacherId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }


        this.scoreTypeRepo.addScoreType(classDetailId, request.getScoreTypeId());
    }


    @Override
    public void deleteScoreType(String classDetailId, String scoreTypeId) {
        this.scoreTypeRepo.deleteScoreType(classDetailId, scoreTypeId);
    }

    @Override
    public ScoreType getScoreTypeById(String id) {
        return this.scoreTypeRepo.findById(id);
    }
}