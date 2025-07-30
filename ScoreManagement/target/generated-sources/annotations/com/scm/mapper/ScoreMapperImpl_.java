package com.scm.mapper;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.pojo.Score;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-30T15:13:49+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ScoreMapperImpl_ implements ScoreMapper {

    @Override
    public Score toScore(ScoreRequest request) {
        if ( request == null ) {
            return null;
        }

        Score score = new Score();

        score.setScore( request.getScore() );

        return score;
    }

    @Override
    public List<Score> toListScore(List<ScoreRequest> dto) {
        if ( dto == null ) {
            return null;
        }

        List<Score> list = new ArrayList<Score>( dto.size() );
        for ( ScoreRequest scoreRequest : dto ) {
            list.add( toScore( scoreRequest ) );
        }

        return list;
    }

    @Override
    public ScoreResponse toScoreResponse(Score dto) {
        if ( dto == null ) {
            return null;
        }

        ScoreResponse scoreResponse = new ScoreResponse();

        scoreResponse.setId( dto.getId() );
        scoreResponse.setScore( dto.getScore() );
        scoreResponse.setStudent( dto.getStudent() );
        scoreResponse.setScoreType( dto.getScoreType() );
        scoreResponse.setClassDetails( dto.getClassDetails() );

        return scoreResponse;
    }
}
