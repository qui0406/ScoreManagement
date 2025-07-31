package com.scm.services.Impl;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.EnrollDetailsMapper;
import com.scm.pojo.EnrollDetails;
import com.scm.repositories.EnrollDetailsRepository;
import com.scm.services.EnrollDetailsService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollDetailsServiceImpl implements EnrollDetailsService {
    @Autowired
    private EnrollDetailsRepository enrollDetailsRepository;

    @Autowired
    private EnrollDetailsMapper enrollDetailsMapper;

    @Override
    public void create(EnrollClassRequest request, String studentId) {
        request.setStudentId(studentId);
        if(enrollDetailsRepository.checkExist(request.getClassDetailId(), studentId)){
            throw new AppException(ErrorCode.CLASS_EXISTED);
        }
        this.enrollDetailsRepository.create(this.enrollDetailsMapper.toEnrollDetails(request));
    }

    @Override
    public void delete(String enrollId) {
        this.enrollDetailsRepository.delete(this.enrollDetailsRepository.findById(enrollId));
    }


}
