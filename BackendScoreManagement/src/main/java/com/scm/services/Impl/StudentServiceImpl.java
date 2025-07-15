/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.responses.StudentResponse;
import com.scm.pojo.Student;
import com.scm.repositories.StudentRepository;
import com.scm.services.StudentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepo;

    @Override
    public List<StudentResponse> getStudentsByClassSubjectId(Integer classSubjectId) {
        List<Student> students = this.studentRepo.getStudentsByClassSubjectId(classSubjectId);
        return students.stream()
                .map(this::convertToStudentResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse convertToStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setMssv(student.getMssv());

        // Chỉ lấy thông tin firstName, lastName và email từ User entity
        if (student.getUser() != null) {
            response.setFirstName(student.getUser().getFirstName());
            response.setLastName(student.getUser().getLastName());
            response.setEmail(student.getUser().getEmail());
        }

        return response;
    }
}
