package com.scm.repositories;

import com.scm.pojo.Teacher;

public interface TeacherRepository {
    Teacher findTeacherById(Integer id);

    String findIdByUsername(String username);

    void updateRoleTeacher(String teacherId);
}
