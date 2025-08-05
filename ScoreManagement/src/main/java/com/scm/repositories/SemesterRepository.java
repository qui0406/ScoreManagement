package com.scm.repositories;

import com.scm.dto.responses.SemesterResponse;
import com.scm.pojo.Semester;

import java.util.List;

public interface SemesterRepository {
    Semester findById(String id);
    void create(Semester semester);
    void delete(Semester semester);

    List<Semester> getAllSemesters();
}
