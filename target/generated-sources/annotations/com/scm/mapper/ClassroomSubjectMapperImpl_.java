package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.pojo.Classroom;
import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-21T16:56:38+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ClassroomSubjectMapperImpl_ implements ClassroomSubjectMapper {

    @Override
    public ClassroomSubject toClassroomSubject(ClassroomSubjectRequest dto) {
        if ( dto == null ) {
            return null;
        }

        ClassroomSubject classroomSubject = new ClassroomSubject();

        classroomSubject.setSemester( dto.getSemester() );

        return classroomSubject;
    }

    @Override
    public ClassroomSubjectResponse toClassroomSubjectResponse(ClassroomSubject dto) {
        if ( dto == null ) {
            return null;
        }

        ClassroomSubjectResponse classroomSubjectResponse = new ClassroomSubjectResponse();

        classroomSubjectResponse.setId( dto.getId() );
        classroomSubjectResponse.setSubject( subjectToSubjectDTO( dto.getSubject() ) );
        classroomSubjectResponse.setSemester( dto.getSemester() );
        classroomSubjectResponse.setClassroom( classroomToClassroomDTO( dto.getClassroom() ) );

        return classroomSubjectResponse;
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
