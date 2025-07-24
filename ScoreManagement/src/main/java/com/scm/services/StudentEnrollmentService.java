package com.scm.services;

import com.scm.pojo.ClassSubject;
import com.scm.pojo.StudentEnrollment;

public interface StudentEnrollmentService {
    ClassSubject findAllByClassSubjectId(String studentId);
}
