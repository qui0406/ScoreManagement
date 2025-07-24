package com.scm.services;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.pojo.Semester;

public interface SemesterService {
    SemesterResponse getSemesterById(String id);
    void create(SemesterRequest semester);
    void delete(SemesterRequest semester);
}
