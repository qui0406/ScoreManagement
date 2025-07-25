package com.scm.mapper.decorator;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.mapper.ScoreMapper;
import com.scm.mapper.UserMapper;
import com.scm.pojo.*;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.repositories.ScoreTypeRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
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
    private ClassroomSubjectRepository classSubjectRepo;
    @Autowired
    private ScoreTypeRepository scoreTypeRepo;
    @Autowired
    private TeacherRepository teacherRepo;

    @Override
    public Score toGrade(ScoreRequest dto) {
        Score score = new Score();
        score.setId(dto.getId());
        score.setScore(dto.getScore());

        Student student = studentRepo.findStudentById(dto.getStudentId());
        ClassSubject classSubject = classSubjectRepo.findClassroomSubjectById(dto.getClassSubjectId());
        ScoreType scoreType = scoreTypeRepo.findScoreTypeById(dto.getScoreTypeId());
        Teacher teacher = teacherRepo.findTeacherById(dto.getTeacherId());

        score.setStudent(student);
        score.setClassSubject(classSubject);
        score.setScoreType(scoreType);
        return score;
    }

    @Override
    public List<Score> toListScore(List<ScoreRequest> dto) {
        List<Score> scores = new ArrayList<>();

        for (ScoreRequest scoreRequest : dto) {
            Score score = new Score();
            score.setId(scoreRequest.getId());
            score.setScore(scoreRequest.getScore());

            Student student = studentRepo.findStudentById(scoreRequest.getStudentId());
            ClassSubject classSubject = classSubjectRepo.findClassroomSubjectById(scoreRequest.getClassSubjectId());
            ScoreType scoreType = scoreTypeRepo.findScoreTypeById(scoreRequest.getScoreTypeId());

            score.setStudent(student);
            score.setClassSubject(classSubject);
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
