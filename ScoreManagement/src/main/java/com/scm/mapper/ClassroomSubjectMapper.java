package com.scm.mapper;


import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.mapper.decorator.ClassroomSubjectDecorator;
import com.scm.mapper.decorator.ScoreMapperDecorator;
import com.scm.pojo.ClassroomSubject;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(ClassroomSubjectDecorator.class)
public interface ClassroomSubjectMapper {
    ClassroomSubject INSTANCE = Mappers.getMapper(ClassroomSubject.class);

    ClassroomSubject toClassroomSubject(ClassroomSubjectRequest dto);

    ClassroomSubjectResponse toClassroomSubjectResponse(ClassroomSubject dto);
}
