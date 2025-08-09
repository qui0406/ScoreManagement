/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.scm.dto.requests.Recipient;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.dto.responses.StudentResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.repositories.StudentRepository;
import com.scm.services.RedisService;
import com.scm.services.StudentService;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<StudentResponse> getAllStudentsByClass(String classDetailId) {
        String cacheKey = "classDetailsAllStudents:" + classDetailId;
        Object cached = redisService.getValue(cacheKey);
        if (cached != null) {
            return (List<StudentResponse>) cached;
        }

        List<Student> students = this.studentRepo.getAllStudentsByClass(classDetailId);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            StudentResponse response = userMapper.toStudentResponse(student);
            studentResponses.add(response);
        }

        redisService.setValue(cacheKey, studentResponses);
        return studentResponses;
    }

    @Override
    public List<Recipient> getAllRecipientStudentsByClass(String classDetailId) {
        List<Student> students = this.studentRepo.getAllStudentsByClass(classDetailId);
        List<Recipient> recipients = new ArrayList<>();
        for (Student student : students) {
            Recipient recipient = new Recipient();
            recipient.setEmail(student.getEmail());
            recipient.setName(student.getFirstName());
            recipients.add(recipient);
        }
        return recipients;
    }

    @Override
    public String findIdByUsername(String username) {
        return this.studentRepo.findIdByUsername(username);
    }

    @Override
    public String getIdByMssv(String mssv) {
        Student student = studentRepo.getIdByMssv(mssv);
        return student.getId().toString();
    }

    @Override
    public List<String> getAllMssvByClass(String classDetailId) {
        return this.studentRepo.getAllMssvByClass(classDetailId);
    }
}