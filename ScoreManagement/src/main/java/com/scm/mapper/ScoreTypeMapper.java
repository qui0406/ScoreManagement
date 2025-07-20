package com.scm.mapper;

import com.scm.dto.SubjectDTO;
import com.scm.pojo.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreTypeMapper {
    SubjectDTO toSubjectDTO(Subject subject);
}