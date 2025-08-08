/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Student;
import com.scm.pojo.Teacher;

import java.util.List;

/**
 *
 * @author admin
 */
public interface StudentRepository {
    Student findById(String id);
    String findIdByUsername(String username);
    Student getIdByMssv(String mssv);
    List<Student> getAllStudentsByClass(String classDetailId);
    List<String> getAllMssvByClass(String classDetailId);
}