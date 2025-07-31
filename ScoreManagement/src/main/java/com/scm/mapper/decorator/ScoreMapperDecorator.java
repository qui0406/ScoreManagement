package com.scm.mapper.decorator;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.mapper.ScoreMapper;
import com.scm.pojo.*;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ScoreTypeRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
@Slf4j
public class ScoreMapperDecorator implements ScoreMapper {
    @Autowired
    @Qualifier("delegate")
    private ScoreMapper delegate;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private ClassDetailsRepository classDetailsRepo;
    @Autowired
    private ScoreTypeRepository scoreTypeRepo;
    @Autowired
    private TeacherRepository teacherRepo;

    @Override
    public Score toScore(ScoreRequest request) {
        Score score = new Score();
        score.setScore(request.getScore());

        Student student = studentRepo.findById(request.getStudentId());
        ClassDetails classDetails = classDetailsRepo.findById(request.getClassDetailId());
        ScoreType scoreType = scoreTypeRepo.findById(request.getScoreTypeId());

        score.setStudent(student);
        score.setClassDetails(classDetails);
        score.setScoreType(scoreType);
        return score;
    }

    @Override
    public List<Score> toListScore(List<ScoreRequest> dto) {
        List<Score> scores = new ArrayList<>();

        for (ScoreRequest scoreRequest : dto) {
            Score score = new Score();
            score.setScore(scoreRequest.getScore());

            Student student = studentRepo.findById(scoreRequest.getStudentId().toString());
            ClassDetails classDetails = classDetailsRepo.findById(scoreRequest.getClassDetailId().toString());
            ScoreType scoreType = scoreTypeRepo.findById(scoreRequest.getScoreTypeId().toString());

            score.setStudent(student);
            score.setClassDetails(classDetails);
            score.setScoreType(scoreType);
            scores.add(score);
        }

        return scores;


    }

    @Override
    public ScoreResponse toScoreResponse(Score dto) {
        return delegate.toScoreResponse(dto);
    }
}
