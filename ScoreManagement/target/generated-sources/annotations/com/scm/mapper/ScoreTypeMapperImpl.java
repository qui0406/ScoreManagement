package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-26T14:26:21+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ScoreTypeMapperImpl implements ScoreTypeMapper {

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
}
