/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.dto.responses;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.SubjectDTO;
import lombok.*;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailsResponse {
    private Integer id;
    private SubjectDTO subject;
    private ClassroomDTO classroom;

    private Integer totalStudents;
    private Integer countScoreType;
}