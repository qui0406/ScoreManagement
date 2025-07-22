package com.scm.mapper.decorator;

import com.scm.dto.*;
import com.scm.dto.requests.ScoreTableRequest;
import com.scm.dto.responses.ScoreTableResponse;
import com.scm.mapper.ClassroomMapper;
import com.scm.mapper.ScoreMapper;
import com.scm.mapper.ScoreTableMapper;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.*;
import com.scm.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class ScoreTableMapperDecorator implements ScoreTableMapper {
    @Autowired
    @Qualifier("delegate")
    private ScoreTableMapper delegate;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private ClassroomSubjectRepository classSubjectRepo;
    @Autowired
    private ScoreTypeRepository scoreTypeRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private ScoreRepository scoreRepo;


    @Override
    public ScoreTableResponse toScoreTableResponse(Score score) {
        Student student = studentRepo.findStudentById(score.getStudent().getId());
        ClassroomSubject classSubject = classSubjectRepo.findClassroomSubjectById(score.getClassSubject().getId());
        ScoreType scoreType = scoreTypeRepo.findScoreTypeById(score.getScoreType().getId());
        Teacher teacher = teacherRepo.findTeacherById(score.getTeacher().getId());

        // Lấy tất cả điểm theo loại điểm cho cùng một student và classSubject
        List<Score> allScores = scoreRepo.findScoreByStudentIdAndClassSubjectId(student.getId(), classSubject.getId());

        // Nhóm các điểm theo loại điểm (ScoreType)
        Map<Integer, ScoreByTypeDTO> scoresMap = new HashMap<>();

        for (Score sc : allScores) {
            int typeId = sc.getScoreType().getId();
            ScoreByTypeDTO dto = scoresMap.getOrDefault(typeId, new ScoreByTypeDTO(
                    typeId,
                    sc.getScoreType().getScoreTypeName(),
                    new ArrayList<>()
            ));

            dto.getScores().add(sc.getScore());
            scoresMap.put(typeId, dto);
        }

        // Build response
        ScoreTableResponse response = new ScoreTableResponse();

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

        response.setTeacher(new TeacherDTO(
                teacher.getMsgv(),
                teacher.getFirstName() + " " + teacher.getLastName()
        ));

        response.setScores(new ArrayList<>(scoresMap.values()));
        log.info(response.getStudent().getName());
        return response;
    }

    @Override
    public Score toScore(ScoreTableRequest scoreTableRequest) {
        Score s = new Score();
        s.setStudent(studentRepo.findStudentById(Integer.parseInt(scoreTableRequest.getStudentId())));
        s.setTeacher(teacherRepo.findTeacherById(Integer.parseInt(scoreTableRequest.getTeacherId())));
        s.setClassSubject(classSubjectRepo.findClassroomSubjectById(Integer.parseInt(scoreTableRequest.getClassSubjectId())));
        return s;
    }
}
