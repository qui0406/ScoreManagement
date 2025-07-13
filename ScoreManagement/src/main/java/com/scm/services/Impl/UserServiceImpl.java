/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scm.dto.responses.UserResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.ClassRoom;
import com.scm.pojo.Student;
import com.scm.pojo.User;
import com.scm.repositories.ClassRoomRepository;
import com.scm.repositories.UserRepository;
import com.scm.services.UserService;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author QUI
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ClassRoomRepository classRoomRepo;

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
    public Student register(Map<String, String> params, MultipartFile avatar) {
        Student u = new Student();
        String mssv = params.get("mssv");
        String name=params.get("firstName");
        String email=params.get("email");

        u.setFirstName(name);
        u.setLastName(params.get("lastName"));
        u.setUsername(params.get("username"));

        String expectedEmail = mssv + name+ "@ou.deu.vn";
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
            schoolYear = df.parse(rawDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
        ClassRoom classRoom = classRoomRepo.getClassRoomById(classroomId);

        if (classRoom == null) {
            throw new RuntimeException("Không tìm thấy lớp học với ID: " + classroomId);
        }
        u.setClassroom(classRoom);

        return this.userRepo.register(u);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.getAllUsers();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }
}
