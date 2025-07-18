package com.scm.dto.requests;

import com.scm.pojo.Classroom;
import com.scm.pojo.Faculty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherRequest extends  UserRequest{
    private String msgv;
    private String position;
    private String experience;
    private Classroom classroom;
    private Faculty faculty;
}
