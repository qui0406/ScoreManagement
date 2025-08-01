package com.scm.mapper;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreStudentResponse;
//import com.scm.mapper.decorator.ScoreStudentMapperDecorator;

import com.scm.dto.responses.WriteScoreStudentPDFResponse;
import com.scm.pojo.Score;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScoreStudentMapper {
    ScoreStudentResponse toScoreResponse(Score score);
    Score toScore(ScoreRequest scoreRequest);

}
