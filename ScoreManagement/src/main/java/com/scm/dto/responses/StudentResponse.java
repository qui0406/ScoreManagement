package com.scm.dto.responses;


import com.scm.pojo.Classroom;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse extends UserResponse{
    int id;
    String mssv;
    Date schoolYear;
    Classroom classroom;
}
