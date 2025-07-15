/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.GradeTypeRequest;
import com.scm.dto.responses.GradeTypeResponse;
import com.scm.pojo.GradeType;
import com.scm.repositories.GradeTypeRepository;
import com.scm.services.GradeTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class GradeTypeServiceImpl implements GradeTypeService {
    @Autowired
    private GradeTypeRepository gradeTypeRepo;

    private static final int MAX_GRADE_TYPES = 5;

    @Override
    public List<GradeTypeResponse> getGradeTypesByClassSubject(Integer classSubjectId) {
        // Đảm bảo có tối thiểu điểm giữa kỳ và cuối kỳ
        this.ensureMinimumGradeTypes(classSubjectId);

        List<GradeType> gradeTypes = this.gradeTypeRepo.getGradeTypesByClassSubject(classSubjectId);
        List<GradeTypeResponse> responses = new ArrayList<>();

        for (GradeType gradeType : gradeTypes) {
            GradeTypeResponse response = new GradeTypeResponse();
            response.setId(gradeType.getId());
            response.setGradeTypeName(gradeType.getGradeTypeName());
            responses.add(response);
        }

        return responses;
    }

//    @Override
//    public List<GradeTypeResponse> getAllGradeTypes() {
//        List<GradeType> gradeTypes = this.gradeTypeRepo.getAllGradeTypes();
//        List<GradeTypeResponse> responses = new ArrayList<>();
//
//        for (GradeType gradeType : gradeTypes) {
//            GradeTypeResponse response = new GradeTypeResponse();
//            response.setId(gradeType.getId());
//            response.setGradeTypeName(gradeType.getGradeTypeName());
//            responses.add(response);
//        }
//
//        return responses;
//    }

    @Override
    public void addGradeTypeToClassSubject(GradeTypeRequest gradeTypeRequest, Integer classSubjectId) {
        // Kiểm tra giới hạn 5 cột
        if (!this.canAddMoreGradeTypes(classSubjectId)) {
            throw new RuntimeException("Không thể thêm quá " + MAX_GRADE_TYPES + " loại điểm!");
        }

        GradeType gradeType = new GradeType();
        gradeType.setGradeTypeName(gradeTypeRequest.getGradeTypeName());

        this.gradeTypeRepo.addOrUpdateGradeType(gradeType);
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
