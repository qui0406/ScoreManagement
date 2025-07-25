package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate openRegistration;
    private LocalDate closeRegistration;
}
