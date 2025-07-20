/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.dto.requests;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
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
public class ScoreRequest {
    private Integer id;
    private BigDecimal score;
    private Integer studentId;
    private Integer classSubjectId;
    private Integer scoreTypeId;
    private Integer teacherId;
}