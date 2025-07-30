/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.ClassDetails;
import com.scm.pojo.Classroom;
import com.scm.pojo.Student;

import java.util.List;

/**
 *
 * @author admin
 */

public interface ClassroomRepository {
    List<Classroom> getListClassRoom();
    Classroom findById(String classroomId);

    String getIdByClassName(String className);

    List<Student> getStudentsInClassroom(String classroomId);

    Classroom create(Classroom classroom);
    void delete(Classroom classroom);
}