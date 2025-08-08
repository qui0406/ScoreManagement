package com.scm.mapper;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.pojo.Semester;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T21:03:38+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class SemesterMapperImpl implements SemesterMapper {

    @Override
    public Semester toSemester(SemesterRequest semesterRequest) {
        if ( semesterRequest == null ) {
            return null;
        }

        Semester semester = new Semester();

        semester.setName( semesterRequest.getName() );
        semester.setStartDate( semesterRequest.getStartDate() );
        semester.setEndDate( semesterRequest.getEndDate() );
        semester.setOpenRegistration( semesterRequest.getOpenRegistration() );
        semester.setCloseRegistration( semesterRequest.getCloseRegistration() );

        return semester;
    }

    @Override
    public SemesterResponse toSemesterResponse(Semester semester) {
        if ( semester == null ) {
            return null;
        }

        SemesterResponse semesterResponse = new SemesterResponse();

        if ( semester.getId() != null ) {
            semesterResponse.setId( String.valueOf( semester.getId() ) );
        }
        semesterResponse.setName( semester.getName() );
        semesterResponse.setStartDate( semester.getStartDate() );
        semesterResponse.setEndDate( semester.getEndDate() );
        semesterResponse.setOpenRegistration( semester.getOpenRegistration() );
        semesterResponse.setCloseRegistration( semester.getCloseRegistration() );

        return semesterResponse;
    }
}
