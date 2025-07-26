package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreTypeMapper {
    SubjectDTO toSubjectDTO(Subject subject);

    ScoreTypeResponse scoreTypeResponse(ScoreType scoreType);
}