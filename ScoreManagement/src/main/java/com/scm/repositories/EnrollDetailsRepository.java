package com.scm.repositories;

import com.scm.pojo.EnrollDetails;
import com.scm.pojo.Student;

import java.util.List;

public interface EnrollDetailsRepository {
    EnrollDetails findById(String enrollDetailId);

    void create(EnrollDetails enrollDetails);
    void delete(EnrollDetails enrollDetails);

    boolean checkExist(String enrollId, String studentId);
}
