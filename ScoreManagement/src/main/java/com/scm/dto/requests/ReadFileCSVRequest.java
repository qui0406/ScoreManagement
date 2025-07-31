package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadFileCSVRequest {
    private String fullName;
    private String mssv;
    private String className;
    private Map<Integer, BigDecimal> scores;
}
