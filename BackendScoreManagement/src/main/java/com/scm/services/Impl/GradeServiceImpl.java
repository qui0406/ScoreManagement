/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.requests.GradeRequest;
import com.scm.dto.responses.GradeResponse;
import com.scm.pojo.*;
import com.scm.repositories.GradeRepository;
import com.scm.services.GradeService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service
@Transactional
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeRepository gradeRepo;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdateGrade(GradeRequest gradeRequest, String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();

        // Kiểm tra xem điểm đã tồn tại chưa
        Grade existingGrade = this.gradeRepo.getGradeByStudentAndClassSubjectAndType(
            gradeRequest.getStudentId(),
            gradeRequest.getClassSubjectId(),
            gradeRequest.getGradeTypeId()
        );

        Grade grade;
        if (existingGrade != null) {
            // Cập nhật điểm hiện có
            grade = existingGrade;
            grade.setScore(gradeRequest.getScore());
        } else {
            // Tạo điểm mới
            grade = new Grade();

            // Lấy các entity liên quan
            Student student = s.get(Student.class, gradeRequest.getStudentId());
            ClassroomSubject classSubject = s.get(ClassroomSubject.class, gradeRequest.getClassSubjectId());
            GradeType gradeType = s.get(GradeType.class, gradeRequest.getGradeTypeId());
            Teacher teacher = s.get(Teacher.class, Long.parseLong(teacherId));

            grade.setStudent(student);
            grade.setClassSubject(classSubject);
            grade.setGradeType(gradeType);
            grade.setTeacher(teacher);
            grade.setScore(gradeRequest.getScore());
        }

        this.gradeRepo.addOrUpdateGrade(grade);
    }

    @Override
    public List<GradeResponse> getGradesByClassSubjectId(Integer classSubjectId) {
        List<Grade> grades = this.gradeRepo.getGradesByClassSubjectId(classSubjectId);
        List<GradeResponse> responses = new ArrayList<>();

        for (Grade grade : grades) {
            GradeResponse response = new GradeResponse();
            response.setId(grade.getId());
            response.setScore(grade.getScore());

            // Thông tin sinh viên
            if (grade.getStudent() != null) {
                response.setStudentId(grade.getStudent().getId());
                response.setMssv(grade.getStudent().getMssv());
                if (grade.getStudent().getUser() != null) {
                    response.setStudentName(grade.getStudent().getUser().getFirstName() + " " + grade.getStudent().getUser().getLastName());
                }
            }

            // Thông tin lớp môn
            if (grade.getClassSubject() != null) {
                response.setClassSubjectId(grade.getClassSubject().getId());
            }

            // Thông tin loại điểm
            if (grade.getGradeType() != null) {
                response.setGradeTypeId(grade.getGradeType().getId());
                response.setGradeTypeName(grade.getGradeType().getGradeTypeName()); // Sửa từ getName() thành getGradeTypeName()
            }

            // Thông tin giảng viên
            if (grade.getTeacher() != null && grade.getTeacher().getUser() != null) {
                response.setTeacherName(grade.getTeacher().getUser().getFirstName() + " " + grade.getTeacher().getUser().getLastName());
            }

            responses.add(response);
        }

        return responses;
    }
}
