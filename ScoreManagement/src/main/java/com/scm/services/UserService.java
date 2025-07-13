/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.services;

import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.Student;
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
    Student register(Map<String, String> params, MultipartFile avatar);
    boolean authenticate(String username, String password);
    List<User> getAllUsers();
    UserResponse getProfile(Principal principal);
}
