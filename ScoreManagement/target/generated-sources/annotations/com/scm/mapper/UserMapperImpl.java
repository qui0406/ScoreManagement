package com.scm.mapper;

import com.scm.dto.requests.UpdateUserRequest;
import com.scm.mapper.decorator.UserMapperDecorator;
import com.scm.pojo.Student;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-26T21:07:40+0700",
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
}
