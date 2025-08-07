package com.scm.mapper;

import com.scm.dto.requests.StudentRegisterRequest;
import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.requests.UpdateUserRequest;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.TeacherResponse;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T08:44:49+0700",
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

        return studentResponse.build();
    }

    @Override
    public Student toStudentUpdate(UpdateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        Student student = new Student();

        student.setFirstName( request.getFirstName() );
        student.setLastName( request.getLastName() );
        student.setUsername( request.getUsername() );
        student.setPassword( request.getPassword() );
        student.setPhone( request.getPhone() );
        student.setGender( request.isGender() );
        student.setAddress( request.getAddress() );
        if ( request.getDob() != null ) {
            student.setDob( Date.from( request.getDob().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }

        return student;
    }

    @Override
    public Student toStudent(StudentRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        Student student = new Student();

        student.setFirstName( request.getFirstName() );
        student.setLastName( request.getLastName() );
        student.setUsername( request.getUsername() );
        student.setPassword( request.getPassword() );
        student.setEmail( request.getEmail() );
        student.setPhone( request.getPhone() );
        student.setGender( request.isGender() );
        student.setAddress( request.getAddress() );
        student.setDob( request.getDob() );
        student.setActive( request.isActive() );
        student.setRole( request.getRole() );
        student.setMssv( request.getMssv() );
        student.setSchoolYear( request.getSchoolYear() );

        return student;
    }

    @Override
    public Teacher toTeacher(TeacherRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setFirstName( request.getFirstName() );
        teacher.setLastName( request.getLastName() );
        teacher.setUsername( request.getUsername() );
        teacher.setPassword( request.getPassword() );
        teacher.setEmail( request.getEmail() );
        teacher.setPhone( request.getPhone() );
        teacher.setGender( request.isGender() );
        teacher.setAddress( request.getAddress() );
        teacher.setDob( request.getDob() );
        teacher.setActive( request.isActive() );
        teacher.setRole( request.getRole() );
        teacher.setMsgv( request.getMsgv() );
        if ( request.getExperience() != null ) {
            teacher.setExperience( String.valueOf( request.getExperience() ) );
        }
        teacher.setPosition( request.getPosition() );

        return teacher;
    }
}
