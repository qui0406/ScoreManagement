package com.scm.dto.requests;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CSVScoreRequest {

    @CsvBindByName(column = "studentId")
    private Integer studentId;
    @CsvBindByName(column = "classSubjectId")
    private Integer classSubjectId;
    @CsvBindByName(column = "scoreTypeId")
    private Integer scoreTypeId;
    @CsvBindByName(column = "teacherId")
    private Integer teacherId;

    @CsvBindByName(column = "score1")
    private BigDecimal score1;

    @CsvBindByName(column = "score2")
    private BigDecimal score2;

    @CsvBindByName(column = "score3")
    private BigDecimal score3;

    @CsvBindByName(column = "score4")
    private BigDecimal score4;

    @CsvBindByName(column = "score5")
    private BigDecimal score5;

    @CsvBindByName(column = "score6")
    private BigDecimal score6;

    @CsvBindByName(column = "score7")
    private BigDecimal score7;

    public Map<Integer, BigDecimal> getScoreMap() {
        Map<Integer, BigDecimal> map = new HashMap<>();
        if (score1 != null) map.put(1, score1);
        if (score2 != null) map.put(2, score2);
        if (score3 != null) map.put(3, score3);
        if (score4 != null) map.put(4, score4);
        if (score5 != null) map.put(5, score5);
        if (score6 != null) map.put(6, score6);
        if (score7 != null) map.put(7, score7);
        return map;
    }
}
