package com.scm.dto.requests;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CSVScoreRequest {
    @CsvBindByName(column = "score")
    private BigDecimal score;
    @CsvBindByName(column = "studentId")
    private Integer studentId;
    @CsvBindByName(column = "classSubjectId")
    private Integer classSubjectId;
    @CsvBindByName(column = "scoreTypeId")
    private Integer scoreTypeId;
    @CsvBindByName(column = "teacherId")
    private Integer teacherId;
}
