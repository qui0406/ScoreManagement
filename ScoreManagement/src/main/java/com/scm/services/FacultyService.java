package com.scm.services;

import com.scm.dto.requests.FacultyRequest;
import com.scm.dto.responses.FacultyResponse;

import java.util.List;

public interface FacultyService {
    void create(FacultyRequest request);
    void delete(String id);

    List<FacultyResponse> getAllFaculties();
}
