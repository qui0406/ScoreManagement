package com.scm.mapper.decorator;

import com.scm.dto.*;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.mapper.ScoreStudentMapper;
import com.scm.pojo.ClassSubject;
import com.scm.pojo.Score;
import com.scm.pojo.Student;
import com.scm.repositories.*;
import com.scm.services.ScoreService;
import com.scm.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class ScoreStudentMapperDecorator implements ScoreStudentMapper {
    @Autowired
    @Qualifier("delegate")
    private ScoreStudentMapper delegate;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private ClassroomSubjectRepository classSubjectRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private ScoreRepository scoreRepo;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public ScoreStudentResponse toScoreResponse(Score score) {
        Student student = studentRepo.findStudentById(score.getStudent().getId());
        ClassSubject classSubject = classSubjectRepo.findClassroomSubjectById(score.getClassSubject().getId());

        Map<Integer, ScoreByTypeDTO> scoresMap = scoreService.getGroupedScores(student.getId().toString(), classSubject.getId().toString());
        TeacherDTO teacherDTO = teacherService.getTeacherDTOById(classSubject.getTeacher().getId().toString());

        ScoreStudentResponse response = new ScoreStudentResponse();

        response.setStudent(new StudentDTO(
                student.getMssv(),
                student.getFirstName() + " " + student.getLastName()
        ));

        response.setSubject(new SubjectDTO(
                classSubject.getSubject().getId(),
                classSubject.getSubject().getSubjectName()
        ));

        response.setClassroom(new ClassroomDTO(
                classSubject.getClassroom().getId(),
                classSubject.getClassroom().getName()
        ));

        response.setTeacher(teacherDTO);
        response.setScores(new ArrayList<>(scoresMap.values()));

        return response;
    }

    @Override
    public Score toScore(ScoreRequest scoreRequest) {
        return null;
    }

}
