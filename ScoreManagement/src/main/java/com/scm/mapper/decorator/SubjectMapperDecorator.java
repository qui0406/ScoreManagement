package com.scm.mapper.decorator;

import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.mapper.SubjectMapper;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Faculty;
import com.scm.pojo.Semester;
import com.scm.pojo.Subject;
import com.scm.repositories.FacultyRepository;
import com.scm.repositories.SemesterRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public abstract class SubjectMapperDecorator implements SubjectMapper {
    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Subject toSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectRequest.getSubjectName());

        Faculty faculty = facultyRepository.findById(subjectRequest.getFacultyId());
        Semester semester = semesterRepository.findById(subjectRequest.getSemesterId());
        subject.setFaculty(faculty);
        subject.setSemester(semester);
        return subject;
    }

    @Override
    public SubjectResponse toSubjectResponse(Subject subject) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId().toString());
        subjectResponse.setSubjectName(subject.getSubjectName());

        return subjectResponse;
    }
}
