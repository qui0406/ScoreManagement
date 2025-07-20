package com.scm.mapper;

import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.mapper.decorator.CSVScoreMapperDecorator;
import com.scm.mapper.decorator.ScoreMapperDecorator;
import com.scm.pojo.Score;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(CSVScoreMapperDecorator.class)
public interface CSVScoreMapper {

    Score toScore(CSVScoreRequest dto);

}

