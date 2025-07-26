/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scm.dto.requests.UpdateUserRequest;
import com.scm.dto.responses.UserResponse;
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

    @Autowired
    private ClassroomRepository classRoomRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FacultyRepository falcutyRepo;


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
    public Student registerStudent(Map<String, String> params, MultipartFile avatar) {
        Student u = new Student();
        String mssv = params.get("mssv");
        String name=params.get("firstName");
        String email=params.get("email");

        u.setFirstName(name);
        u.setLastName(params.get("lastName"));
        u.setUsername(params.get("username"));

        String expectedEmail = mssv + name+ "@ou.edu.vn";
        if (!email.equalsIgnoreCase(expectedEmail)) {
            throw new RuntimeException("Email không đúng định dạng. Phải là: " + expectedEmail);
        }

        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setMssv(mssv);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date schoolYear = null;
        try {
            String rawDate = params.get("schoolYear");
            if (rawDate != null && !rawDate.isBlank()) {
                schoolYear = df.parse(rawDate);
            }
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format for schoolYear", e);
        }
        u.setSchoolYear(schoolYear);

        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setRole("ROLE_USER");
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                return null;
            }
        }
        Integer classroomId = Integer.parseInt(params.get("classroom"));
        Classroom classRoom = classRoomRepo.findClassRoomById(classroomId);

        log.info(classRoom.toString());

        if (classRoom == null) {
            throw new RuntimeException("Không tìm thấy lớp học với ID: " + classroomId);
        }


        u.setClassroom(classRoom);

        return this.userRepo.studentRegister(u);
    }

    @Override
    public Teacher registerTeacher(Map<String, String> params, MultipartFile avatar) {
        Teacher u = new Teacher();

        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setUsername(params.get("username"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setMsgv(params.get("msgv"));

        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setRole("ROLE_TEACHER");
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                return null;
            }
        }
        Integer classroomId = Integer.parseInt(params.get("classroom"));
        Classroom classRoom = classRoomRepo.findClassRoomById(classroomId);

        log.info(classRoom.toString());

        if (classRoom == null) {
            throw new RuntimeException("Không tìm thấy lớp học với ID: " + classroomId);
        }
        u.setClassroom(classRoom);

        Integer falcutyId = Integer.parseInt(params.get("faculty"));

        log.info(falcutyId.toString());

        Faculty falcuty = falcutyRepo.findFacultyById(falcutyId);

        if (falcuty == null) {
            throw new RuntimeException("Không tìm thấy khoa với ID: " + falcutyId);
        }

        u.setFaculty(falcuty);

        return this.userRepo.teacherRegister(u);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.getAllUsers();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }
//
//    @Override
//    public UserResponse update(UpdateUserRequest request) {
////        Student u = userMapper.toStudentUpdate(request);
////        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
////        MultipartFile avatar = request.getAvatar();
////        if (!avatar.isEmpty()) {
////            try {
////                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
////                u.setAvatar(res.get("secure_url").toString());
////            } catch (IOException ex) {
////                return null;
////            }
////        }
////        return userMapper.toUserResponse(this.userRepo.update(u));
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
