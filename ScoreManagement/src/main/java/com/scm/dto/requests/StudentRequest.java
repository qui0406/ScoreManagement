package com.scm.dto.requests;

import com.scm.pojo.Classroom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest extends UserRequest {
    private String mssv;
    private Classroom classroom;
    private Date schoolYear;
}
