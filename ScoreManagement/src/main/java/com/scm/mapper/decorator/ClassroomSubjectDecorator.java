package com.scm.mapper.decorator;

import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.mapper.ClassroomMapper;
import com.scm.mapper.ClassroomSubjectMapper;
import com.scm.mapper.ScoreMapper;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.ClassroomSubject;
import com.scm.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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



    @Override
    public ClassroomSubject toClassroomSubject(ClassroomSubjectRequest dto) {
        ClassroomSubject cs = new ClassroomSubject();

        cs.setSemester(dto.getSemester());

        var teacher = teacherRepo.findTeacherById(dto.getTeacherId());
        var classId = classRepo.findClassRoomById(dto.getClassroomId());
        var subject = subjectRepo.findSubjectById(dto.getSubjectId());
        var student = studentRepo.findStudentById(dto.getStudentId());


        if (teacher == null || classId == null || subject == null || student == null) {
            throw new IllegalArgumentException("One or more related entities not found");
        }

        cs.setStudent(student);
        cs.setTeacher(teacher);
        cs.setClassroom(classId);
        cs.setSubject(subject);
        return cs;
    }

    @Override
    public ClassroomSubjectResponse toClassroomSubjectResponse(ClassroomSubject dto) {
        ClassroomSubjectResponse cs = new ClassroomSubjectResponse();
        if (dto.getSubject() == null && dto.getClassroom() == null) {
            throw new IllegalArgumentException("One or more related entities not found");
        }
        log.info("fuugdvshb:{}", dto.getId().toString());
        log.info("semeter: {}",dto.getSemester().toString());
        log.info("classroom :{}",dto.getClassroom().getName().toString());
        log.info("subject :{}", dto.getSubject().getSubjectName().toString());

        cs.setId(dto.getId());
        cs.setSemester(dto.getSemester());
        cs.setSubject(subjectMapper.toSubjectDTO(dto.getSubject()));
        cs.setClassroom(classroomMapper.toClassroomDTO(dto.getClassroom()));
        return cs;
    }
}
