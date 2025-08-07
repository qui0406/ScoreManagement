package com.scm.services;

import com.scm.dto.TeacherDTO;
import com.scm.dto.responses.TeacherResponse;
import com.scm.pojo.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    TeacherDTO getTeacherDTOById(String teacherId);

    String findIdByUsername(String username);

    List<TeacherResponse> getAllTeachersByRole(String role, String page);

    void updateRoleTeacher(String teacherId);
    void downRoleTeacher(String teacherId);

    void delete(String teacherId);
}
