package com.scm.mapper;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.mapper.decorator.CSVScoreMapperDecorator;
import com.scm.mapper.decorator.SemesterMapperDecorator;
import com.scm.pojo.Semester;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(SemesterMapperDecorator.class)
public interface SemesterMapper {
    Semester toSemester(SemesterRequest semesterRequest);
    SemesterResponse toSemesterResponse(Semester semester);
}
