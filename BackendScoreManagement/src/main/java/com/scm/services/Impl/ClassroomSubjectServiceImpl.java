/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.pojo.ClassroomSubject;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.services.ClassroomSubjectService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ClassroomSubjectServiceImpl implements ClassroomSubjectService {
    @Autowired
    private ClassroomSubjectRepository classroomSubjectRepo;

    @Override
    public List<ClassroomSubjectResponse> getClassroomSubjectsByTeacherId(String teacherId) {
        List<ClassroomSubject> classroomSubjects = this.classroomSubjectRepo.getClassroomSubjectsByTeacherId(teacherId);
        List<ClassroomSubjectResponse> responses = new ArrayList<>();

        for (ClassroomSubject cs : classroomSubjects) {
            ClassroomSubjectResponse response = new ClassroomSubjectResponse();
            response.setClassSubjectId(cs.getId());
            response.setSemester(cs.getSemester());

            // Thông tin lớp học
            if (cs.getClass_id() != null) {
                response.setClassroomName(cs.getClass_id().getName());
            }

            // Thông tin môn học
            if (cs.getSubject() != null) {
                response.setSubjectName(cs.getSubject().getSubjectName()); // Sửa từ getName() thành getSubjectName()
            }

            // Đếm số sinh viên và loại điểm
            response.setTotalStudents(this.classroomSubjectRepo.countStudentsInClassSubject(cs.getId()));
            response.setGradeTypesCount(this.classroomSubjectRepo.countGradeTypesInClassSubject(cs.getId()));

            responses.add(response);
        }

        return responses;
    }

    @Override
    public ClassroomSubjectResponse getClassroomSubjectDetails(Integer classSubjectId) {
        ClassroomSubject cs = this.classroomSubjectRepo.getClassroomSubjectById(classSubjectId);

        if (cs == null) {
            return null;
        }

        ClassroomSubjectResponse response = new ClassroomSubjectResponse();
        response.setClassSubjectId(cs.getId());
        response.setSemester(cs.getSemester());

        // Thông tin lớp học
        if (cs.getClass_id() != null) {
            response.setClassroomName(cs.getClass_id().getName());
        }

        // Thông tin môn học
        if (cs.getSubject() != null) {
            response.setSubjectName(cs.getSubject().getSubjectName()); // Sửa từ getName() thành getSubjectName()
        }

        // Đếm số sinh viên và loại điểm
        response.setTotalStudents(this.classroomSubjectRepo.countStudentsInClassSubject(cs.getId()));
        response.setGradeTypesCount(this.classroomSubjectRepo.countGradeTypesInClassSubject(cs.getId()));

        return response;
    }
}
