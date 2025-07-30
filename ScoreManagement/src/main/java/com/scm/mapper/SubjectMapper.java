package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.mapper.decorator.SubjectMapperDecorator;
import com.scm.pojo.Subject;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(SubjectMapperDecorator.class)
public interface SubjectMapper {
    SubjectDTO toSubjectDTO(Subject subject);

    Subject toSubject(SubjectRequest subjectRequest);

    SubjectResponse toSubjectResponse(Subject subject);
}