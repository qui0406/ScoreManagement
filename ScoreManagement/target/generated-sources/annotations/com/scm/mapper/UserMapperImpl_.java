package com.scm.mapper;

import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-13T20:15:04+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class UserMapperImpl_ implements UserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        return userResponse;
    }

    @Override
    public TeacherResponse toTeacherResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse teacherResponse = new TeacherResponse();

        return teacherResponse;
    }

    @Override
    public StudentResponse toStudentResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();

        return studentResponse;
    }
}
