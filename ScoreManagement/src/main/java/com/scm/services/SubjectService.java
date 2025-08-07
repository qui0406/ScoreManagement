package com.scm.services;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    SubjectResponse create(SubjectRequest subject);

    void delete(String subjectId);

    List<ClassDetailsResponse> getAllSubjectsByStudent(String studentId, Map<String, String> params);

    List<SubjectResponse> getAllSubjectsBySemester(String studentId, String semesterId);

    List<SubjectResponse> getAllSubjects(String page);
}
