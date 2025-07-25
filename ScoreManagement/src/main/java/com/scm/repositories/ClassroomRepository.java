/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Classroom;
import com.scm.pojo.Student;

import java.util.List;

/**
 *
 * @author admin
 */

public interface ClassroomRepository {
    List<Classroom> getClassroomsByTeacherId(String teacherId);
    List<Classroom> getListClassRoom();
    Classroom findClassRoomById(Integer classroomId);

    List<Student> getStudentsInClassroom(String classroomId);

}