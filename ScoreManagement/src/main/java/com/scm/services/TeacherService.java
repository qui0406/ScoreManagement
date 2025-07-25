package com.scm.services;

import com.scm.dto.TeacherDTO;

public interface TeacherService {
    TeacherDTO getTeacherDTOById(String teacherId);

    String findIdByUsername(String username);
}
