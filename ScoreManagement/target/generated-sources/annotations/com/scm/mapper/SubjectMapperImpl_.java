package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T08:44:50+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class SubjectMapperImpl_ implements SubjectMapper {

    @Override
    public SubjectDTO toSubjectDTO(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId( subject.getId() );
        subjectDTO.setSubjectName( subject.getSubjectName() );

        return subjectDTO;
    }

    @Override
    public Subject toSubject(SubjectRequest subjectRequest) {
        if ( subjectRequest == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setSubjectName( subjectRequest.getSubjectName() );

        return subject;
    }

    @Override
    public SubjectResponse toSubjectResponse(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectResponse subjectResponse = new SubjectResponse();

        if ( subject.getId() != null ) {
            subjectResponse.setId( String.valueOf( subject.getId() ) );
        }
        subjectResponse.setSubjectName( subject.getSubjectName() );

        return subjectResponse;
    }
}
