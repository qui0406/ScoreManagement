package com.scm.repositories;

import com.scm.pojo.Faculty;

import java.util.List;

public interface FacultyRepository {
    Faculty findById(String facultyId);

    List<Faculty> findAllFaculties();

    Faculty create(Faculty faculty);
    void delete(Faculty faculty);
}
