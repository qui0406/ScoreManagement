/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.mapper;

import com.scm.dto.requests.UserRequest;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
//    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
    TeacherResponse toTeacherResponse(Teacher teacher);
    StudentResponse toStudentResponse(Student student);
}
