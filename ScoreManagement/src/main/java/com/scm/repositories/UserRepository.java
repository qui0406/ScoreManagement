/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Student;
import com.scm.pojo.User;

import java.util.List;


/**
 *
 * @author QUI
 */
public interface UserRepository {
    Student getUserByUsername(String username);
    Student register(Student student);
    boolean authenticate(String username, String password);
    List<User> getAllUsers();
}
