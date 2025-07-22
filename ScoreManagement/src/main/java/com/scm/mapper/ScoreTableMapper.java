package com.scm.mapper;

import com.scm.dto.requests.ScoreTableRequest;
import com.scm.dto.responses.ScoreTableResponse;
import com.scm.mapper.decorator.ClassroomSubjectDecorator;
import com.scm.mapper.decorator.ScoreTableMapperDecorator;
import com.scm.pojo.Score;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(ScoreTableMapperDecorator.class)
public interface ScoreTableMapper {
    ScoreTableMapper INSTANCE = Mappers.getMapper(ScoreTableMapper.class);

    Score toScore(ScoreTableRequest scoreTableRequest);
    ScoreTableResponse toScoreTableResponse(Score score);
}
