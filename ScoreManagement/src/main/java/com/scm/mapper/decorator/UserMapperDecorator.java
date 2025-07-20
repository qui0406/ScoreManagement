package com.scm.mapper.decorator;

import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public abstract class UserMapperDecorator implements UserMapper {
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public UserResponse toUserResponse(User user) {
        if (user instanceof Teacher teacher) {
            return toTeacherResponse(teacher); // dùng bản override bên dưới
        } else if (user instanceof Student student) {
            return toStudentResponse(student); // dùng bản override bên dưới
        } else {
            return mapBasicUser(user);
        }
    }

    private UserResponse mapBasicUser(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId().toString());
        res.setUsername(user.getUsername());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setGender(user.isGender());
        res.setDob(user.getDob());
        res.setAddress(user.getAddress());
        res.setRole(user.getRole());
        res.setAvatar(user.getAvatar());
        return res;

    }

    @Override
    public TeacherResponse toTeacherResponse(Teacher teacher) {
        TeacherResponse res = delegate.toTeacherResponse(teacher);
        res.setId(teacher.getId().toString());
        res.setUsername(teacher.getUsername());
        res.setFirstName(teacher.getFirstName());
        res.setLastName(teacher.getLastName());
        res.setEmail(teacher.getEmail());
        res.setPhone(teacher.getPhone());
        res.setGender(teacher.isGender());
        res.setDob(teacher.getDob());
        res.setAddress(teacher.getAddress());
        res.setRole(teacher.getRole());
        res.setAvatar(teacher.getAvatar());
        res.setClassroom(teacher.getClassroom());
        res.setExperience(teacher.getExperience());
        res.setPosition(teacher.getPosition());
        res.setMsgv(teacher.getMsgv());
        res.setFaculty(teacher.getFaculty());
        return res;
    }

    @Override
    public StudentResponse toStudentResponse(Student student) {
        StudentResponse res = delegate.toStudentResponse(student);
        res.setId(student.getId().toString());
        res.setUsername(student.getUsername());
        res.setFirstName(student.getFirstName());
        res.setLastName(student.getLastName());
        res.setEmail(student.getEmail());
        res.setPhone(student.getPhone());
        res.setGender(student.isGender());
        res.setDob(student.getDob());
        res.setAddress(student.getAddress());
        res.setRole(student.getRole());
        res.setAvatar(student.getAvatar());
        res.setMssv(student.getMssv());
        res.setSchoolYear(student.getSchoolYear());
        res.setClassroom(student.getClassroom());
        return res;
    }

}