/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.dto.responses;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.SubjectDTO;
import com.scm.pojo.Classroom;
import com.scm.pojo.Subject;
import lombok.*;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSubjectResponse {
    private Integer id;
    private SubjectDTO subject;
    private String semester;
    private ClassroomDTO classroom;

    private Integer totalStudents;
    private Integer countScoreType;
}