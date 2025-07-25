package com.scm.repositories;

import com.scm.pojo.Subject;

import java.util.List;

public interface SubjectRepository {
    Subject findSubjectById(Integer id);

    List<Subject> getAllSubjectsByStudentId(String studentId);

    List<Subject> getAllSubjectsInFacultySemester(String facultyId, String semesterId);

    void create(Subject subject);

    void delete(Subject subject);
}
