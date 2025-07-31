package com.scm.services;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    void create(SubjectRequest subject);

    void delete(String subjectId);

    List<SubjectResponse> getAllSubjectsByStudent(String studentId, Map<String, String> params);

    List<SubjectResponse> getAllSubjectsBySemester(String studentId, String semesterId);

}
