package com.scm.repositories;

import com.scm.pojo.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectRepository {
    Subject findById(String id);

    List<Subject> getAllSubjectsByStudentId(String studentId, Map<String, String> params);

    List<Subject> getAllSubjectsInFacultySemester(String facultyId, String semesterId);

    Subject create(Subject subject);

    void delete(Subject subject);

    List<Subject> getSubjectsCurrentSemester(String studentId, String semesterId);

    List<Subject> getAllSubjects(String page);
}
