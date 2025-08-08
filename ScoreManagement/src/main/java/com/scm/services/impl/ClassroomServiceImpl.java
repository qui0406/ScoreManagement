/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.responses.ClassResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ClassroomMapper;
import com.scm.pojo.Classroom;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ClassroomRepository;
import com.scm.services.ClassroomService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepo;

    @Autowired
    private ClassroomMapper classroomMapper;


    @Override
    public ClassResponse create(ClassroomRequest request) {
        Classroom classroom = classroomMapper.toClassroom(request);
        return classroomMapper.toClassroomResponse(this.classroomRepo.create(classroom));
    }

    @Override
    public void delete(String classroomId) {
        if (classroomId != null) {
            this.classroomRepo.delete(this.classroomRepo.findById(classroomId));
        }
        throw new AppException(ErrorCode.INVALID_DATA);
    }

    @Override
    public String getIdByClassName(String className) {
        return this.classroomRepo.getIdByClassName(className);
    }
}