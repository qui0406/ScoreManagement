package com.scm.dto.requests;

import jakarta.validation.constraints.NotBlank;
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
public class StudentRegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String role;
    private String email;
    @NotBlank
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String phone;
    private String address;
    private boolean active;
    private boolean gender;
    private String mssv;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date schoolYear;
}
