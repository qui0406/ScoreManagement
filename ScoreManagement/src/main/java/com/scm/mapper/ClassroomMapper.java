package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.responses.ClassResponse;
import com.scm.pojo.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomDTO toClassroomDTO(Classroom classroom);

    Classroom toClassroom(ClassroomRequest request);
    ClassResponse toClassroomResponse(Classroom classroom);
}
