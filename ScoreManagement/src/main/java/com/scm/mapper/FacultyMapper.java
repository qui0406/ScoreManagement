package com.scm.mapper;

import com.scm.dto.requests.FacultyRequest;
import com.scm.dto.responses.FacultyResponse;
import com.scm.pojo.Faculty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    List<FacultyResponse> toFacultyRequest(List<Faculty> faculty);
    Faculty toFaculty(FacultyRequest facultyRequest);
}
