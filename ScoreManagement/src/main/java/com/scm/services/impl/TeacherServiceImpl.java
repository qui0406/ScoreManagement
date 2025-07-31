package com.scm.services.impl;

import com.scm.dto.TeacherDTO;
import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
import com.scm.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public TeacherDTO getTeacherDTOById(String teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId);
        return new TeacherDTO(teacher.getMsgv(), teacher.getFirstName() + " " + teacher.getLastName());
    }

    @Override
    public String findIdByUsername(String username) {
        return this.teacherRepo.findIdByUsername(username);
    }

    @Override
    public List<TeacherResponse> getAllTeachersByRole(Map<String , String> params) {
        List<Teacher> listTeachers = this.teacherRepo.getAllTeachersByRole(params);

        List<TeacherResponse> responses = new ArrayList<>();
        for (Teacher teacher : listTeachers) {
            responses.add(this.userMapper.toTeacherResponse(teacher));
        }
        return responses;
    }

    @Override
    public void updateRoleTeacher(String teacherId) {
        this.teacherRepo.updateRoleTeacher(teacherId);
    }
}
