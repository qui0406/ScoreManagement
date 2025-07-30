/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ScoreTypeMapper;
import com.scm.pojo.ScoreType;
import com.scm.repositories.ScoreTypeRepository;
import com.scm.services.ScoreTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ScoreTypeServiceImpl implements ScoreTypeService {
    @Autowired
    private ScoreTypeRepository scoreTypeRepo;

    @Autowired
    private ScoreTypeMapper scoreTypeMapper;


    private static final int MAX_GRADE_TYPES = 5;


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
    public void addScoreType(String classDetailId, String scoreTypeId) {
        if(scoreTypeId.equals("1") || scoreTypeId.equals("2") ) {
            throw new AppException(ErrorCode.SCORE_TYPE_INCORRECT);
        }
        this.scoreTypeRepo.addScoreType(classDetailId, scoreTypeId);
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