package com.scm.mapper;

import com.scm.dto.requests.FacultyRequest;
import com.scm.dto.responses.FacultyResponse;
import com.scm.pojo.Faculty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T14:53:56+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class FacultyMapperImpl implements FacultyMapper {

    @Override
    public List<FacultyResponse> toFacultyRequest(List<Faculty> faculty) {
        if ( faculty == null ) {
            return null;
        }

        List<FacultyResponse> list = new ArrayList<FacultyResponse>( faculty.size() );
        for ( Faculty faculty1 : faculty ) {
            list.add( toFacultyResponse( faculty1 ) );
        }

        return list;
    }

    @Override
    public Faculty toFaculty(FacultyRequest facultyRequest) {
        if ( facultyRequest == null ) {
            return null;
        }

        Faculty faculty = new Faculty();

        faculty.setName( facultyRequest.getName() );
        faculty.setDescription( facultyRequest.getDescription() );

        return faculty;
    }

    @Override
    public FacultyResponse toFacultyResponse(Faculty faculty) {
        if ( faculty == null ) {
            return null;
        }

        FacultyResponse facultyResponse = new FacultyResponse();

        if ( faculty.getId() != null ) {
            facultyResponse.setId( String.valueOf( faculty.getId() ) );
        }
        facultyResponse.setName( faculty.getName() );
        facultyResponse.setDescription( faculty.getDescription() );

        return facultyResponse;
    }
}
