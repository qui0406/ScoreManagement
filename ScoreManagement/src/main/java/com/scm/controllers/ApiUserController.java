/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.LoginRequest;
import com.scm.dto.requests.StudentRegisterRequest;
import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import com.scm.responses.ApiResponse;
import com.scm.services.UserService;

import java.util.Collections;
import java.util.Map;

import com.scm.utils.JwtUtils;
import com.scm.validators.StudentEmailValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author QUI
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class ApiUserController {

    @Autowired
    private UserService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (this.userDetailsService.authenticate(request.getUsername(), request.getPassword())) {
            try {
                User student = userDetailsService.getUserByUsername(request.getUsername());
                String getRole = student.getRole();
                String token = JwtUtils.generateToken(request.getUsername(), getRole);
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }


    @PostMapping(path = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@ModelAttribute @Valid StudentRegisterRequest request,
                                    @RequestParam(value = "avatar")
                                    MultipartFile avatar) {
        try{
            StudentResponse studentResponse = userDetailsService.registerStudent(request, avatar);
            return ResponseEntity.ok(studentResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
