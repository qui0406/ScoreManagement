package com.scm.mapper;


import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassDetailsResponse;
//import com.scm.mapper.decorator.ClassDetailMapperDecorator;
import com.scm.dto.responses.ClassResponse;
import com.scm.mapper.decorator.ClassDetailMapperDecorator;
import com.scm.pojo.ClassDetails;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(ClassDetailMapperDecorator.class)
public interface ClassDetailMapper {
    ClassDetails INSTANCE = Mappers.getMapper(ClassDetails.class);

    ClassDetails toClassDetails(CreateClassDetailsRequest dto);

    ClassDetailsResponse toClassDetailsResponse(ClassDetails dto);
    ClassResponse toClassroomResponse(ClassDetails request);
}
