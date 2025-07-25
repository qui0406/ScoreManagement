/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.responses.StudentResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.repositories.StudentRepository;
import com.scm.services.StudentService;

import java.util.ArrayList;
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

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<StudentResponse> getStudentsByClassSubjectId(Integer classSubjectId) {
        List<Student> students = this.studentRepo.getStudentsByClassSubjectId(classSubjectId);

        List<StudentResponse> studentResponses = new ArrayList<>();

        for (Student student : students) {
            StudentResponse response = userMapper.toStudentResponse(student);
            studentResponses.add(response);
        }
        return studentResponses;
    }

    @Override
    public String findIdByUsername(String username) {
        return this.studentRepo.findIdByUsername(username);
    }
}