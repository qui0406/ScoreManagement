package com.scm.repositories;

import com.scm.pojo.Subject;

public interface SubjectRepository {
    Subject findSubjectById(Integer id);

}
