package com.scm.mapper;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
//import com.scm.mapper.decorator.ScoreMapperDecorator;
import com.scm.mapper.decorator.ScoreMapperDecorator;
import com.scm.pojo.Score;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(ScoreMapperDecorator.class)
public interface ScoreMapper {
    ScoreMapper INSTANCE = Mappers.getMapper(ScoreMapper.class);
    Score toScore(ScoreRequest request);

    List<Score> toListScore(List<ScoreRequest> dto);

    ScoreResponse toScoreResponse(Score dto);

}
