/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.scm.dto.responses.ClassroomResponse;
import com.scm.pojo.Classroom;
import com.scm.repositories.ClassroomRepository;
import com.scm.services.ClassroomService;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ClassroomResponse> getClassroomsByTeacherId(String teacherId) {
        List<Classroom> classrooms = this.classroomRepo.getClassroomsByTeacherId(teacherId);
        List<ClassroomResponse> responses = new ArrayList<>();

        for (Classroom classroom : classrooms) {
            ClassroomResponse response = new ClassroomResponse();
            response.setId(classroom.getId());
            response.setName(classroom.getName());
            responses.add(response);
        }

        return responses;
    }


}