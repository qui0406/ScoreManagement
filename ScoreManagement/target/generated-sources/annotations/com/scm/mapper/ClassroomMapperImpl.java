package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.responses.ClassResponse;
import com.scm.pojo.Classroom;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T21:50:59+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ClassroomMapperImpl implements ClassroomMapper {

    @Override
    public ClassroomDTO toClassroomDTO(Classroom classroom) {
        if ( classroom == null ) {
            return null;
        }

        ClassroomDTO classroomDTO = new ClassroomDTO();

        classroomDTO.setId( classroom.getId() );
        classroomDTO.setName( classroom.getName() );

        return classroomDTO;
    }

    @Override
    public Classroom toClassroom(ClassroomRequest request) {
        if ( request == null ) {
            return null;
        }

        Classroom classroom = new Classroom();

        classroom.setName( request.getName() );

        return classroom;
    }

    @Override
    public ClassResponse toClassroomResponse(Classroom classroom) {
        if ( classroom == null ) {
            return null;
        }

        ClassResponse classResponse = new ClassResponse();

        classResponse.setId( classroom.getId() );
        classResponse.setName( classroom.getName() );

        return classResponse;
    }
}
