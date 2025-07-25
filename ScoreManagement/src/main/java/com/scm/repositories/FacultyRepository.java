package com.scm.repositories;

import com.scm.pojo.Faculty;

import java.util.List;

public interface FacultyRepository {
    Faculty findFacultyById(Integer facultytId);

    List<Faculty> findAllFaculties();
}
