package com.scm.mapper.decorator;

import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.ClassResponse;
import com.scm.mapper.ClassroomMapper;
import com.scm.mapper.ClassDetailMapper;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.*;
import com.scm.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClassDetailMapperDecorator implements ClassDetailMapper {
    @Autowired
    @Qualifier("delegate")
    private ClassDetailMapper delegate;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

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
    public ClassDetails toClassDetails(CreateClassDetailsRequest request) {
        ClassDetails details = new ClassDetails();

        Teacher teacher = teacherRepo.findById(request.getTeacherId());
        Classroom classId = classRepo.findById(request.getClassroomId());
        Subject subject = subjectRepo.findById(request.getSubjectId());

        details.setTeacher(teacher);
        details.setClassroom(classId);
        details.setSubject(subject);
        return details;
    }

    @Override
    public ClassDetailsResponse toClassDetailsResponse(ClassDetails dto) {
        ClassDetailsResponse cs = new ClassDetailsResponse();

        cs.setId(dto.getId());
        cs.setSubject(subjectMapper.toSubjectDTO(dto.getSubject()));
        cs.setClassroom(classroomMapper.toClassroomDTO(dto.getClassroom()));
        return cs;
    }

    @Override
    public ClassResponse toClassroomResponse(ClassDetails request) {
        ClassResponse cs = new ClassResponse();
        cs.setId(request.getId());
        cs.setName(request.getClassroom().getName());
        cs.setSubjectName(request.getSubject().getSubjectName());
        return cs;
    }
}
