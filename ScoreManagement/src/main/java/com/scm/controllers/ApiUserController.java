/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Student;
import com.scm.pojo.User;
import com.scm.services.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import com.scm.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiUserController {
    UserService userDetailsService;
    UserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        if (this.userDetailsService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getUsername());
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
        Student u = this.userDetailsService.register(params, avatar);
        StudentResponse studentResponse = userMapper.toStudentResponse(u);
        return ResponseEntity.ok().body(studentResponse);
    }

    @PostMapping(path = "/update-profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestParam Map<String, String> params,
                                    @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        User u = this.userDetailsService.register(params, avatar);
        UserResponse userResponse = userMapper.toUserResponse(u);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return ResponseEntity.ok(this.userDetailsService.getProfile(principal));
    }

}
