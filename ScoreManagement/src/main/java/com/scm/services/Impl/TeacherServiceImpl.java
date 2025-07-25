package com.scm.services.Impl;

import com.scm.dto.TeacherDTO;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
import com.scm.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepo;
    @Override
    public TeacherDTO getTeacherDTOById(String teacherId) {
        Teacher teacher = teacherRepo.findTeacherById(Integer.parseInt(teacherId));
        return new TeacherDTO(teacher.getMsgv(), teacher.getFirstName() + " " + teacher.getLastName());
    }
}
