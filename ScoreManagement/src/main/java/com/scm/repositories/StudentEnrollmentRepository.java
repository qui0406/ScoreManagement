package com.scm.repositories;

import com.scm.pojo.EnrollDetails;

import java.util.List;

public interface StudentEnrollmentRepository {
    List<EnrollDetails> findAllByClassSubjectId(String studentId);

    void create(EnrollDetails enrollDetails);
    void delete(EnrollDetails enrollDetails);
}
