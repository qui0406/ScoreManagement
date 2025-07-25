package com.scm.mapper;


import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.mapper.decorator.ClassroomSubjectDecorator;
import com.scm.pojo.ClassSubject;
import com.scm.pojo.StudentEnrollment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(ClassroomSubjectDecorator.class)
public interface ClassroomSubjectMapper {
    ClassSubject INSTANCE = Mappers.getMapper(ClassSubject.class);

    ClassSubject toClassroomSubject(ClassroomSubjectRequest dto);

    ClassroomSubjectResponse toClassroomSubjectResponse(ClassSubject dto);

    ScoreStudentResponse toScoreTableResponse(StudentEnrollment studentEnrollment);
}
