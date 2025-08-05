package com.scm.mapper;

import com.scm.dto.StudentDTO;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.pojo.Score;
import com.scm.pojo.Student;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T13:01:05+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ScoreStudentMapperImpl implements ScoreStudentMapper {

    @Override
    public ScoreStudentResponse toScoreResponse(Score score) {
        if ( score == null ) {
            return null;
        }

        ScoreStudentResponse scoreStudentResponse = new ScoreStudentResponse();

        scoreStudentResponse.setStudent( studentToStudentDTO( score.getStudent() ) );

        return scoreStudentResponse;
    }

    @Override
    public Score toScore(ScoreRequest scoreRequest) {
        if ( scoreRequest == null ) {
            return null;
        }

        Score score = new Score();

        score.setScore( scoreRequest.getScore() );

        return score;
    }

    protected StudentDTO studentToStudentDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setMssv( student.getMssv() );

        return studentDTO;
    }
}
