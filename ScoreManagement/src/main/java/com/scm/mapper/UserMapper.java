/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.mapper;

import com.scm.dto.requests.*;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.mapper.decorator.UserMapperDecorator;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    TeacherResponse toTeacherResponse(Teacher teacher);

    StudentResponse toStudentResponse(Student student);

    Student toStudentUpdate(UpdateUserRequest request);

    Student toStudent(StudentRegisterRequest request);

    Teacher toTeacher(TeacherRegisterRequest request);
}