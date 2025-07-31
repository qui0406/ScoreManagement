/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

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
import com.scm.repositories.ClassroomRepository;
import com.scm.repositories.FacultyRepository;
import com.scm.repositories.UserRepository;
import com.scm.services.UserService;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    private ClassroomRepository classRoomRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private FacultyRepository falcutyRepo;


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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if(u == null){
            throw new UsernameNotFoundException("Invalid username");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority( u.getRole()));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public StudentResponse registerStudent(StudentRegisterRequest request, MultipartFile avatar) {
        Student u = userMapper.toStudent(request);
        u.setSchoolYear(request.getSchoolYear());

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

//    @Override
//    public UserResponse update(UpdateUserRequest request) {
//        Student u = userMapper.toStudentUpdate(request);
//        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
//        MultipartFile avatar = request.getAvatar();
//        if (!avatar.isEmpty()) {
//            try {
//                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                u.setAvatar(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                return null;
//            }
//        }
//        return userMapper.toUserResponse(this.userRepo.update(u));
//        return null;
//    }
//
//    @Override
//    public boolean checkExistEmail(String email) {
//        return this.userRepo.checkExistEmail(email);
//    }
//
//    @Override
//    public boolean checkExistUsername(String username) {
//        return this.userRepo.checkExistUsername(username);
//    }
}
