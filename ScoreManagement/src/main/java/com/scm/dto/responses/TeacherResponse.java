package com.scm.dto.responses;

import com.scm.pojo.Classroom;
import com.scm.pojo.Faculty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherResponse extends UserResponse{
    int id;
    String experience;
    String position;
    String msgv;
    Classroom classroom;
    Faculty faculty;
}
