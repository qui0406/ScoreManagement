package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegisterRequest {
    private String username;
    private String password;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String phone;
    private String address;
    private boolean active;
    private boolean gender;
    private String msgv;
    private String position;
    private Integer experience;
}
