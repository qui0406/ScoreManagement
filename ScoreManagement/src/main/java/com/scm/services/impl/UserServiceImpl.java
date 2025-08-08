/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scm.dto.requests.StudentRegisterRequest;
import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.requests.UpdateUserRequest;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.UserMapper;
import com.scm.pojo.*;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import com.scm.repositories.UserRepository;
import com.scm.services.StudentService;
import com.scm.services.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author QUI
 */
@Slf4j
@Service("userDetailsService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse getProfile(Principal principal) {
        try{
            User user = userRepo.getUserByUsername(principal.getName());
            return userMapper.toUserResponse(user);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if(u == null){
            throw new AppException(ErrorCode.INVALID_DATA);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority( u.getRole()));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }


    @Override
    public StudentResponse registerStudent(StudentRegisterRequest request, MultipartFile avatar) {
        if(checkExistUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if(checkExistEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        String firstName = request.getFirstName();
        String mssv = request.getMssv();
        String email = request.getEmail();

        String expectedEmail = mssv + firstName + "@ou.edu.vn";

        if(!email.equals(expectedEmail)){
            throw new AppException(ErrorCode.EMAIL_NO_FORRMAT);
        }

        Student u = userMapper.toStudent(request);
        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
        u.setRole("ROLE_USER");

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                throw new AppException(ErrorCode.INVALID_DATA);
            }
        }
        return this.userMapper.toStudentResponse(this.userRepo.studentRegister(u));
    }

    @Override
    public TeacherResponse registerTeacher(TeacherRegisterRequest request, MultipartFile avatar) {
        if(checkExistUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if(checkExistEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        Teacher u = userMapper.toTeacher(request);
        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
        u.setRole("ROLE_TEACHER");
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                return null;
            }
        }
        return this.userMapper.toTeacherResponse(this.userRepo.teacherRegister(u));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.getAllUsers();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public UserResponse update(UpdateUserRequest request, String userId, MultipartFile avatar) {
        User existingUser = userRepo.findById(userId);
        if (existingUser == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!existingUser.getUsername().equals(request.getUsername()) && userRepo.checkExistUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                existingUser.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }
        return userMapper.toUserResponse(userRepo.update(existingUser));
    }

    @Override
    public boolean checkExistEmail(String email) {
        return this.userRepo.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return this.userRepo.checkExistUsername(username);
    }

    @Override
    public String findIdByUserName(String username) {
        return this.userRepo.findIdByUserName(username);
    }
}
