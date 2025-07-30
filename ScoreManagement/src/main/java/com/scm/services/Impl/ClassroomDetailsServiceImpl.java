/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

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

    @Autowired
    private ClassroomDetailsService classroomDetailsService;


//    @Override
//    public List<ClassDetailsResponse> getClassroomSubjectsByTeacherId(String teacherId) {
//        List<ClassDetails> classDetails = this.classroomSubjectRepo
//                .getClassroomSubjectsByTeacherId(teacherId);
//
//        if (!classDetails.isEmpty() && classDetails.get(0).getSubject() != null) {
//            log.info("subject service :{}", classDetails.get(0).getSubject().getSubjectName());
//        } else {
//            log.warn("Subject is null for first classroom subject");
//        }
//        log.info(classDetails.toArray().toString());
//
//        log.info("subject service :{}", classDetails.get(0).getSubject().getSubjectName().toString());
//        List<ClassDetailsResponse> responses = new ArrayList<>();
//        log.info(classDetails.toString());
//        for (ClassDetails cs : classDetails) {
//            ClassDetailsResponse response = classDetailMapper
//                    .toClassroomSubjectResponse(cs);
//            responses.add(response);
//        }
//
//        return responses;
//    }
//
//    @Override
//    public ClassDetailsResponse getClassroomSubjectDetails(String classSubjectId) {
//        ClassDetails cs = this.classroomSubjectRepo.findById(classSubjectId);
//
//        if (cs == null) {
//            return null;
//        }
//
//        ClassDetailsResponse response = classDetailMapper.toClassroomSubjectResponse(cs);
//
//        response.setTotalStudents(this.classroomSubjectRepo.countStudentsInClassSubject(cs.getId()));
//        response.setCountScoreType(this.classroomSubjectRepo.countScoreTypesInClassSubject(cs.getId()));
//        return response;
//    }

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