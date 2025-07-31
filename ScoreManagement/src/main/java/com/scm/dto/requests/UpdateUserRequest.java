package com.scm.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private boolean gender;
    private LocalDate dob;
//    private MultipartFile avatar;
}
