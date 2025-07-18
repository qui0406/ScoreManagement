package com.scm.repositories;

import com.scm.pojo.Faculty;

public interface FacultyRepository {
    Faculty findFacultyById(Integer facultytId);
}
