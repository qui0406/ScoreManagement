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
    date = "2025-07-25T16:13:59+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
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

        if ( user.getId() != null ) {
            userResponse.setId( String.valueOf( user.getId() ) );
        }
        userResponse.setUsername( user.getUsername() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setPhone( user.getPhone() );
        userResponse.setGender( user.isGender() );
        userResponse.setDob( user.getDob() );
        userResponse.setAddress( user.getAddress() );
        userResponse.setRole( user.getRole() );
        userResponse.setAvatar( user.getAvatar() );

        return userResponse;
    }

    @Override
    public TeacherResponse toTeacherResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse.TeacherResponseBuilder teacherResponse = TeacherResponse.builder();

        teacherResponse.experience( teacher.getExperience() );
        teacherResponse.position( teacher.getPosition() );
        teacherResponse.msgv( teacher.getMsgv() );
        teacherResponse.classroom( teacher.getClassroom() );
        teacherResponse.faculty( teacher.getFaculty() );

        return teacherResponse.build();
    }

    @Override
    public StudentResponse toStudentResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse.StudentResponseBuilder studentResponse = StudentResponse.builder();

        studentResponse.mssv( student.getMssv() );
        studentResponse.schoolYear( student.getSchoolYear() );
        studentResponse.classroom( student.getClassroom() );

        return studentResponse.build();
    }
}
