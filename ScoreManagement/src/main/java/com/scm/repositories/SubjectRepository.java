package com.scm.repositories;

import com.scm.pojo.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectRepository {
    Subject findSubjectById(Integer id);

    List<Subject> getAllSubjectsByStudentId(String studentId, Map<String, String> params);

    List<Subject> getAllSubjectsInFacultySemester(String facultyId, String semesterId);

    void create(Subject subject);

    void delete(Subject subject);
}
