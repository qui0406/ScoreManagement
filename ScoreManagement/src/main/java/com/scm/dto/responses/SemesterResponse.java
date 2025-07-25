package com.scm.dto.responses;

import com.scm.pojo.Semester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterResponse {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate openRegistration;
    private LocalDate closeRegistration;
}
