/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;

import java.util.List;


/**
 *
 * @author QUI
 */
public interface UserRepository {
    User getUserByUsername(String username);
    Student studentRegister(Student student);
    Teacher teacherRegister(Teacher teacher);
    boolean authenticate(String username, String password);
    List<User> getAllUsers();


//    User update(User user);
//
//    boolean checkExistEmail(String email);
//    boolean checkExistUsername(String username);
//
//    User findUserByEmail(String email);
}
