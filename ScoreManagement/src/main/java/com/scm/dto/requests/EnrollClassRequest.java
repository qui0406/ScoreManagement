package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollClassRequest {
    private String studentId;
    private String classDetailId;
    private String semesterId;
}
