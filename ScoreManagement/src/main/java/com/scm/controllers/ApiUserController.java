/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import com.scm.services.ScoreStudentService;
import com.scm.services.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import com.scm.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author QUI
 */
@RestController
@RequestMapping("/api/auth")
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiUserController {

    UserService userDetailsService;

    UserMapper userMapper;

    ScoreStudentService scoreStudentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student u) {
        if (this.userDetailsService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                User student = userDetailsService.getUserByUsername(u.getUsername());
                String getRole = student.getRole().toString();
                String token = JwtUtils.generateToken(u.getUsername(), getRole);
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }
    
    @GetMapping(path="/hello")
    public ResponseEntity<?> getUsername(@RequestParam(value="username") String username){
        return ResponseEntity.ok(this.userDetailsService.getUserByUsername(username));
    }

    @PostMapping(path = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestParam Map<String, String> params,
                                    @RequestParam(value = "avatar", required = false) MultipartFile avatar) {

//        if(this.userDetailsService.checkExistUsername(params.get("username"))){
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }
//        if(this.userDetailsService.checkExistEmail(params.get("email"))){
//            throw new AppException(ErrorCode.EMAIL_EXISTED);
//        }
        Student u = this.userDetailsService.registerStudent(params, avatar);
        StudentResponse studentResponse = userMapper.toStudentResponse(u);
        return ResponseEntity.ok().body(studentResponse);
    }

    @PostMapping(path = "/register-teacher",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTeacher(@Valid @RequestParam Map<String, String> params,
                                           @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        Teacher u = this.userDetailsService.registerTeacher(params, avatar);
        TeacherResponse teacherResponse = userMapper.toTeacherResponse(u);
        return ResponseEntity.ok().body(teacherResponse);
    }






}
