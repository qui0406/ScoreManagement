/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.StudentRegisterRequest;
import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author QUI
 */

public interface UserService extends UserDetailsService{
    User getUserByUsername(String username);
    StudentResponse registerStudent(StudentRegisterRequest request, MultipartFile avatar);
    TeacherResponse registerTeacher(TeacherRegisterRequest request, MultipartFile avatar);
    boolean authenticate(String username, String password);
    List<User> getAllUsers();
    UserResponse getProfile(Principal principal);

//    UserResponse update(UpdateUserRequest request);
//
//    boolean checkExistEmail(String email);
//    boolean checkExistUsername(String username);
}
