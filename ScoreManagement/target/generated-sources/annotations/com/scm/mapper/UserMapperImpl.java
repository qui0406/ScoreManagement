package com.scm.mapper;

import com.scm.dto.requests.StudentRegisterRequest;
import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.requests.UpdateUserRequest;
import com.scm.mapper.decorator.UserMapperDecorator;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T19:25:35+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Primary
public class UserMapperImpl extends UserMapperDecorator {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public Student toStudentUpdate(UpdateUserRequest request)  {
        return delegate.toStudentUpdate( request );
    }

    @Override
    public Student toStudent(StudentRegisterRequest request)  {
        return delegate.toStudent( request );
    }

    @Override
    public Teacher toTeacher(TeacherRegisterRequest request)  {
        return delegate.toTeacher( request );
    }
}
