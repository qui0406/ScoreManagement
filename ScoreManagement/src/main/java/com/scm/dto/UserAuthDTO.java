package com.scm.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthDTO {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
    boolean gender;
    LocalDate dob;
    String address;
    String role;
    String avatar;
    String token;
}
