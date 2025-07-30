package com.scm.dto.responses;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriteScoreStudentPDFResponse {
    private String fullName;
    private String mssv;
    private String className;
    private Map<Integer, BigDecimal> scores;

}
