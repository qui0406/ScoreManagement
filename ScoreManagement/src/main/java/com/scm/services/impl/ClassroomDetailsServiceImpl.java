/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassResponse;
import com.scm.mapper.ClassDetailMapper;
import com.scm.pojo.ClassDetails;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.services.ClassroomDetailsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
@Service
@Slf4j
public class ClassroomDetailsServiceImpl implements ClassroomDetailsService {
    @Autowired
    private ClassDetailsRepository classDetailsRepository;
    @Autowired
    private ClassDetailMapper classDetailMapper;


    @Override
    public List<ClassResponse> getClassroomsByTeacherId(String teacherId) {
        List<ClassDetails> classrooms = this.classDetailsRepository.getClassroomsByTeacherId(teacherId);
        List<ClassResponse> responses = new ArrayList<>();

        for (ClassDetails classroom : classrooms) {
            ClassResponse response = classDetailMapper.toClassroomResponse(classroom);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void create(CreateClassDetailsRequest createClassDetailsRequest) {
        classDetailsRepository.create(classDetailMapper.toClassDetails(createClassDetailsRequest));
    }

    @Override
    public void delete(String classDetailsId) {
        this.classDetailsRepository.delete(classDetailsId);
    }
}