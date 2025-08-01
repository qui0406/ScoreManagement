package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.mapper.decorator.SubjectMapperDecorator;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T13:19:53+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Primary
public class SubjectMapperImpl extends SubjectMapperDecorator {

    @Autowired
    @Qualifier("delegate")
    private SubjectMapper delegate;

    @Override
    public SubjectDTO toSubjectDTO(Subject subject)  {
        return delegate.toSubjectDTO( subject );
    }
}
