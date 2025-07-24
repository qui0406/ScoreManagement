package com.scm.repositories;

import com.scm.dto.responses.SemesterResponse;
import com.scm.pojo.Semester;

public interface SemesterRepository {
    Semester getSemesterById(String id);
    void create(Semester semester);
    void delete(Semester semester);
}
