package com.scm.services;

import com.scm.dto.requests.SubjectRequest;
import com.scm.pojo.Subject;

public interface SubjectService {
    void create(SubjectRequest subject);

    void delete(String subjectId);
}
