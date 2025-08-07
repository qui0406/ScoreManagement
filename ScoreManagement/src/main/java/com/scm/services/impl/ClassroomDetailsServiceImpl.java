/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.ClassResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ClassDetailMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.EnrollDetails;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.EnrollDetailsRepository;
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
    @Autowired
    private EnrollDetailsRepository enrollDetailsRepository;


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

    @Override
    public String getTeacherIdByClassDetailId(String classDetailId) {
        return this.classDetailsRepository.findById(classDetailId).getTeacher().getId().toString();
    }

    @Override
    public ClassDetailsResponse getClassDetailsById(String classDetailsId, String studentId) {
        //List<ClassDetails> e = this.enrollDetailsRepository.findAllClassDetailRegisterSemester(studentId);
        ClassDetails classDetails = this.classDetailsRepository.findById(classDetailsId);
//        if(!e.contains(classDetails)) {
//            throw new AppException(ErrorCode.UNAUTHORIZED);
//        }

        ClassDetailsResponse response = classDetailMapper.toClassDetailsResponse(classDetails);
        response.setTotalStudents(this.classDetailsRepository.countStudent(classDetails));
        return response;
    }

    @Override
    public List<ClassDetailsResponse> getClassroomByStudentId(String studentId) {
        List<ClassDetails> classrooms = this.classDetailsRepository.getClassroomByStudentId(studentId);
        List<ClassDetailsResponse> responses = new ArrayList<>();
        for (ClassDetails classroom : classrooms) {
            ClassDetailsResponse response = classDetailMapper.toClassDetailsResponse(classroom);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<ClassDetailsResponse> getAllClassDetails() {
        List<ClassDetails> classDetails = this.classDetailsRepository.getAllClassDetails();

        List<ClassDetailsResponse> responses = new ArrayList<>();
        for (ClassDetails classDetail : classDetails) {
            ClassDetailsResponse response = classDetailMapper.toClassDetailsResponse(classDetail);
            responses.add(response);
        }
        return responses;
    }
}