package com.scm.dto.responses;

import com.scm.pojo.Classroom;
import com.scm.pojo.Faculty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherResponse extends UserResponse{
    String experience;
    String position;
    String msgv;
    Faculty faculty;
}