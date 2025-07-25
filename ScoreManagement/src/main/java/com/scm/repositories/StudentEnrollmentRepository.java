package com.scm.repositories;

import com.scm.pojo.ClassSubject;
import com.scm.pojo.Student;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Subject;

import java.util.List;

public interface StudentEnrollmentRepository {
    List<StudentEnrollment> findAllByClassSubjectId(String studentId);

    void create(StudentEnrollment studentEnrollment);
    void delete(StudentEnrollment studentEnrollment);
}
