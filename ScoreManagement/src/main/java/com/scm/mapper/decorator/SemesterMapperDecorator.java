package com.scm.mapper.decorator;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.mapper.SemesterMapper;
import com.scm.pojo.Semester;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.repositories.ScoreRepository;
import com.scm.repositories.StudentRepository;
import com.scm.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class SemesterMapperDecorator implements SemesterMapper {
    @Autowired
    @Qualifier("delegate")
    private SemesterMapper delegate;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private ClassroomSubjectRepository classSubjectRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private ScoreRepository scoreRepo;

    @Override
    public Semester toSemester(SemesterRequest semesterRequest) {
        Semester semester = new Semester();
        semester.setName(semesterRequest.getName());
        semester.setStartDate(semesterRequest.getStartDate());
        semester.setEndDate(semesterRequest.getEndDate());
        semester.setOpenRegistration(semesterRequest.getOpenRegistration());
        semester.setCloseRegistration(semesterRequest.getCloseRegistration());
        return semester;
    }

    @Override
    public SemesterResponse toSemesterResponse(Semester semester) {
        SemesterResponse semesterResponse = new SemesterResponse();
        semesterResponse.setName(semester.getId().toString());
        semesterResponse.setName(semester.getName());
        semesterResponse.setStartDate(semester.getStartDate());
        semesterResponse.setEndDate(semester.getEndDate());
        semesterResponse.setOpenRegistration(semester.getOpenRegistration());
        semesterResponse.setCloseRegistration(semester.getCloseRegistration());
        return semesterResponse;
    }
}
