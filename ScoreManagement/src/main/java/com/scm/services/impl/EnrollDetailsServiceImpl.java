package com.scm.services.impl;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.EnrollDetailsMapper;
import com.scm.pojo.EnrollDetails;
import com.scm.repositories.EnrollDetailsRepository;
import com.scm.services.EnrollDetailsService;
import com.scm.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollDetailsServiceImpl implements EnrollDetailsService {
    @Autowired
    private EnrollDetailsRepository enrollDetailsRepository;

    @Autowired
    private EnrollDetailsMapper enrollDetailsMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void create(EnrollClassRequest request, String studentId) {
        request.setStudentId(studentId);
        if(enrollDetailsRepository.checkExist(request.getClassDetailId(), studentId)){
            throw new AppException(ErrorCode.CLASS_EXISTED);
        }
        this.enrollDetailsRepository.create(this.enrollDetailsMapper.toEnrollDetails(request));

        String cacheKey = "classDetailsAllStudents:" + request.getClassDetailId();
        redisService.deleteKey(cacheKey);
    }

    @Override
    public void delete(String enrollId) {
        this.enrollDetailsRepository.delete(this.enrollDetailsRepository.findById(enrollId));
        EnrollDetails enrollDetails = this.enrollDetailsRepository.findById(enrollId);
        String cacheKey = "classDetailsAllStudents:" + enrollDetails.getClassDetails().getId();
        redisService.deleteKey(cacheKey);
    }
}
