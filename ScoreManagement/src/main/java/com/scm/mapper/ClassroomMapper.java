package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.responses.ClassroomResponse;
import com.scm.pojo.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomDTO toClassroomDTO(Classroom classroom);
}
