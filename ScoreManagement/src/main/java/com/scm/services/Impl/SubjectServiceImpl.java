package com.scm.services.Impl;

import com.scm.dto.requests.SubjectRequest;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import com.scm.services.SemesterService;
import com.scm.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void create(SubjectRequest subject) {
        this.subjectRepository.create(subjectMapper.toSubject(subject));
    }

    @Override
    public void delete(String id) {
        this.subjectRepository.delete(this.subjectRepository.findSubjectById(Integer.parseInt(id)));
    }
}
