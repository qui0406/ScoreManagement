package com.scm.mapper.decorator;

import com.scm.dto.*;
import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.mapper.ClassroomMapper;
import com.scm.mapper.ClassroomSubjectMapper;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.*;
import com.scm.repositories.*;
import com.scm.services.ScoreService;
import com.scm.services.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
@Slf4j
public class ClassroomSubjectDecorator implements ClassroomSubjectMapper {
    @Autowired
    @Qualifier("delegate")
    private ClassroomSubjectMapper delegate;

    @Autowired
    private ClassroomSubjectRepository classSubjectRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private ClassroomRepository classRepo;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private SemesterRepository semesterRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ScoreService scoreService;



    @Override
    public ClassSubject toClassroomSubject(ClassroomSubjectRequest dto) {
        ClassSubject cs = new ClassSubject();

        Teacher teacher = teacherRepo.findTeacherById(dto.getTeacherId());
        Classroom classId = classRepo.findClassRoomById(dto.getClassroomId());
        Subject subject = subjectRepo.findSubjectById(dto.getSubjectId());
        Student student = studentRepo.findStudentById(dto.getStudentId());
        Semester semester = semesterRepo.getSemesterById(dto.getSemesterId().toString());


        if (teacher == null || classId == null || subject == null || student == null) {
            throw new IllegalArgumentException("One or more related entities not found");
        }

        cs.setTeacher(teacher);
        cs.setSemester(semester);
        cs.setClassroom(classId);
        cs.setSubject(subject);
        return cs;
    }

    @Override
    public ClassroomSubjectResponse toClassroomSubjectResponse(ClassSubject dto) {
        ClassroomSubjectResponse cs = new ClassroomSubjectResponse();
        if (dto.getSubject() == null && dto.getClassroom() == null) {
            throw new IllegalArgumentException("One or more related entities not found");
        }

        cs.setId(dto.getId());
        ClassroomSubjectResponse.SemesterDTO semesterDto =
                new ClassroomSubjectResponse.SemesterDTO(dto.getSemester().getName());
        cs.setSemester(semesterDto);
        cs.setSubject(subjectMapper.toSubjectDTO(dto.getSubject()));
        cs.setClassroom(classroomMapper.toClassroomDTO(dto.getClassroom()));
        return cs;
    }

    @Override
    public ScoreStudentResponse toScoreTableResponse(StudentEnrollment studentEnrollment) {
        String studentId = studentEnrollment.getStudent().getId().toString();
        String classSubjectId = studentEnrollment.getClassSubject().getId().toString();

        // Gọi từ service thay vì xử lý trực tiếp
        Map<Integer, ScoreByTypeDTO> scoresMap = scoreService.getGroupedScores(studentId, classSubjectId);
        TeacherDTO teacherDTO = teacherService.getTeacherDTOById(studentEnrollment.getClassSubject().getTeacher().getId().toString());

        ScoreStudentResponse response = new ScoreStudentResponse();

        response.setStudent(new StudentDTO(
                studentEnrollment.getStudent().getMssv(),
                studentEnrollment.getStudent().getFirstName() + " " + studentEnrollment.getStudent().getLastName()
        ));

        response.setSubject(new SubjectDTO(
                studentEnrollment.getClassSubject().getSubject().getId(),
                studentEnrollment.getClassSubject().getSubject().getSubjectName()
        ));

        response.setClassroom(new ClassroomDTO(
                studentEnrollment.getClassSubject().getClassroom().getId(),
                studentEnrollment.getClassSubject().getClassroom().getName()
        ));

        response.setTeacher(teacherDTO);
        response.setScores(new ArrayList<>(scoresMap.values()));

        return response;
    }
}
