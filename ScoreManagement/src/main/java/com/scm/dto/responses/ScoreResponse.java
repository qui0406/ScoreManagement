/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.dto.responses;

import com.scm.pojo.ClassDetails;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import lombok.*;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreResponse {
    private Integer id;
    private BigDecimal score;
    private Student student;
    private ScoreType scoreType;
    private Teacher teacher;
    private ClassDetails classDetails;
}