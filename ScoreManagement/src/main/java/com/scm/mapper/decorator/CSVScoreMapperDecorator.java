package com.scm.mapper.decorator;

import com.scm.dto.requests.CSVScoreRequest;
import com.scm.mapper.CSVScoreMapper;
import com.scm.mapper.ScoreMapper;
import com.scm.pojo.Score;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.repositories.ScoreTypeRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CSVScoreMapperDecorator implements CSVScoreMapper {
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
//    @Override
//    public Score toScore(CSVScoreRequest dto) {
//        Score score = new Score();
//        score.setScore(dto.getScore());
//
//        var student = studentRepo.findStudentById(dto.getStudentId());
//        var classSubject = classSubjectRepo.findClassroomSubjectById(dto.getClassSubjectId());
//        var scoreType = scoreTypeRepo.findScoreTypeById(dto.getScoreTypeId());
//        var teacher = teacherRepo.findTeacherById(dto.getTeacherId());
//
//
//        if (student == null || classSubject == null || scoreType == null || teacher == null) {
//            throw new IllegalArgumentException("One or more related entities not found");
//        }
//
//        score.setStudent(student);
//        score.setClassSubject(classSubject);
//        score.setScoreType(scoreType);
//        return score;
//    }
}
