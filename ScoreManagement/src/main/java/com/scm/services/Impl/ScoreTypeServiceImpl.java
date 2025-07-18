/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
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
    private ScoreTypeRepository gradeTypeRepo;

    private static final int MAX_GRADE_TYPES = 5;

    @Override
    public List<ScoreTypeResponse> getScoreTypesByClassSubject(Integer classSubjectId) {
        // Đảm bảo có tối thiểu điểm giữa kỳ và cuối kỳ
        this.ensureMinimumGradeTypes(classSubjectId);

        List<ScoreType> scoreTypes = this.gradeTypeRepo.getGradeTypesByClassSubject(classSubjectId);
        List<ScoreTypeResponse> responses = new ArrayList<>();

        for (ScoreType scoreType : scoreTypes) {
            ScoreTypeResponse response = new ScoreTypeResponse();
            response.setId(scoreType.getId());
            response.setGradeTypeName(scoreType.getScoreTypeName());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public void addGradeTypeToClassSubject(ScoreTypeRequest scoreTypeRequest, Integer classSubjectId) {
        // Kiểm tra giới hạn 5 cột
        if (!this.canAddMoreGradeTypes(classSubjectId)) {
            throw new RuntimeException("Không thể thêm quá " + MAX_GRADE_TYPES + " loại điểm!");
        }

        ScoreType scoreType = new ScoreType();
        scoreType.setScoreTypeName(scoreTypeRequest.getGradeTypeName());

        this.gradeTypeRepo.addOrUpdateGradeType(scoreType);
    }

    @Override
    public void deleteGradeType(Integer id) {
        this.gradeTypeRepo.deleteGradeType(id);
    }

    @Override
    public boolean canAddMoreGradeTypes(Integer classSubjectId) {
        long currentCount = this.gradeTypeRepo.countGradeTypesByClassSubject(classSubjectId);
        return currentCount < MAX_GRADE_TYPES;
    }

    @Override
    public void ensureMinimumGradeTypes(Integer classSubjectId) {
        // Kiểm tra xem đã có đủ loại điểm tối thiểu (giữa kỳ + cuối kỳ) chưa
        // Logic này sẽ được triển khai khi cần thiết
        // Có thể tự động tạo điểm giữa kỳ và cuối kỳ nếu chưa có
    }
}