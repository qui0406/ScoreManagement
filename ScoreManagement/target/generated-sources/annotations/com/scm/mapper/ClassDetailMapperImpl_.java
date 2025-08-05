package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.ClassResponse;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Classroom;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T09:20:58+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ClassDetailMapperImpl_ implements ClassDetailMapper {

    @Override
    public ClassDetails toClassDetails(CreateClassDetailsRequest dto) {
        if ( dto == null ) {
            return null;
        }

        ClassDetails classDetails = new ClassDetails();

        return classDetails;
    }

    @Override
    public ClassDetailsResponse toClassDetailsResponse(ClassDetails dto) {
        if ( dto == null ) {
            return null;
        }

        ClassDetailsResponse classDetailsResponse = new ClassDetailsResponse();

        if ( dto.getId() != null ) {
            classDetailsResponse.setId( String.valueOf( dto.getId() ) );
        }
        classDetailsResponse.setSubject( subjectToSubjectDTO( dto.getSubject() ) );
        classDetailsResponse.setClassroom( classroomToClassroomDTO( dto.getClassroom() ) );

        return classDetailsResponse;
    }

    @Override
    public ClassResponse toClassroomResponse(ClassDetails request) {
        if ( request == null ) {
            return null;
        }

        ClassResponse classResponse = new ClassResponse();

        classResponse.setId( request.getId() );

        return classResponse;
    }

    protected SubjectDTO subjectToSubjectDTO(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId( subject.getId() );
        subjectDTO.setSubjectName( subject.getSubjectName() );

        return subjectDTO;
    }

    protected ClassroomDTO classroomToClassroomDTO(Classroom classroom) {
        if ( classroom == null ) {
            return null;
        }

        ClassroomDTO classroomDTO = new ClassroomDTO();

        classroomDTO.setId( classroom.getId() );
        classroomDTO.setName( classroom.getName() );

        return classroomDTO;
    }
}
