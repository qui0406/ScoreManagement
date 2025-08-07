package com.scm.repositories;

import com.scm.pojo.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherRepository {
    Teacher findById(String id);

    String findIdByUsername(String username);

    void updateRoleTeacher(String teacherId);

    void downRoleTeacher(String teacherId);

    List<Teacher> getAllTeachersByRole(String role, String page);

    void delete(Teacher teacher);
}
