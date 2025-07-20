/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ClassroomSubjectMapper;
import com.scm.pojo.ClassroomSubject;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.services.ClassroomSubjectService;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
@Slf4j
public class ClassroomSubjectServiceImpl implements ClassroomSubjectService {
    @Autowired
    private ClassroomSubjectRepository classroomSubjectRepo;
    @Autowired
    private ClassroomSubjectMapper  classroomSubjectMapper;

    @Override
    public List<ClassroomSubjectResponse> getClassroomSubjectsByTeacherId(String teacherId) {
        List<ClassroomSubject> classroomSubjects = this.classroomSubjectRepo
                .getClassroomSubjectsByTeacherId(teacherId);

        if (!classroomSubjects.isEmpty() && classroomSubjects.get(0).getSubject() != null) {
            log.info("subject service :{}", classroomSubjects.get(0).getSubject().getSubjectName());
        } else {
            log.warn("Subject is null for first classroom subject");
        }
        log.info(classroomSubjects.toArray().toString());

        log.info("subject service :{}", classroomSubjects.get(0).getSubject().getSubjectName().toString());
        List<ClassroomSubjectResponse> responses = new ArrayList<>();
        log.info(classroomSubjects.toString());
        for (ClassroomSubject cs : classroomSubjects) {
            ClassroomSubjectResponse response = classroomSubjectMapper
                    .toClassroomSubjectResponse(cs);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public ClassroomSubjectResponse getClassroomSubjectDetails(Integer classSubjectId) {
        ClassroomSubject cs = this.classroomSubjectRepo.findClassroomSubjectById(classSubjectId);

        if (cs == null) {
            return null;
        }

        ClassroomSubjectResponse response = classroomSubjectMapper.toClassroomSubjectResponse(cs);

        response.setTotalStudents(this.classroomSubjectRepo.countStudentsInClassSubject(cs.getId()));
        response.setCountScoreType(this.classroomSubjectRepo.countScoreTypesInClassSubject(cs.getId()));
        return response;
    }

    @Override
    public ClassroomSubjectResponse create(ClassroomSubjectRequest classroomSubjectRequest, String id) {
        ClassroomSubjectRequest csr = classroomSubjectRequest;
        csr.setStudentId(Integer.parseInt(id));
        ClassroomSubject cs = classroomSubjectMapper.toClassroomSubject(csr);
        if (cs == null) {
            log.info("nullllllllllll");
            throw new AppException(ErrorCode.INVALID_DATA);
        }
        boolean check = classroomSubjectRepo.existClassSubjectRegister(cs);
        if (check) {
            log.info("classroom subject register fail");
            throw new AppException(ErrorCode.EXIST_CLASS);
        }
        ClassroomSubject saved = classroomSubjectRepo.create(cs);
        return classroomSubjectMapper.toClassroomSubjectResponse(saved);
    }

    @Override
    public void delete(String classSubjectId, String userId) {
        this.classroomSubjectRepo.delete(Integer.parseInt(classSubjectId), userId);
    }
}