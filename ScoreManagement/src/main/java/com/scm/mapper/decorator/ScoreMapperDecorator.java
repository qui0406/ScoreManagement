package com.scm.mapper.decorator;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.mapper.ScoreMapper;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Score;
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
        score.setScore(dto.getScore());

        var student = studentRepo.findStudentById(dto.getStudentId());
        var classSubject = classSubjectRepo.findClassroomSubjectById(dto.getClassSubjectId());
        var scoreType = scoreTypeRepo.findScoreTypeById(dto.getScoreTypeId());
        var teacher = teacherRepo.findTeacherById(dto.getTeacherId());


        if (student == null || classSubject == null || scoreType == null || teacher == null) {
            throw new IllegalArgumentException("One or more related entities not found");
        }

        score.setStudent(student);
        score.setClassSubject(classSubject);
        score.setScoreType(scoreType);
        score.setTeacher(teacher);
        return score;
    }

    @Override
    public ScoreResponse toScoreResponse(Score dto) {
        return delegate.toScoreResponse(dto);
    }
}
