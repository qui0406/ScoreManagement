package com.scm.services.impl;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.mapper.SemesterMapper;
import com.scm.pojo.Semester;
import com.scm.repositories.SemesterRepository;
import com.scm.services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterMapper semesterMapper;

    @Override
    public SemesterResponse getSemesterById(String id) {
       return this.semesterMapper.toSemesterResponse(this.semesterRepository.findById(id));
    }

    @Override
    public void create(SemesterRequest semester) {
        this.semesterRepository.create(this.semesterMapper.toSemester(semester));
    }

    @Override
    public void delete(SemesterRequest semester) {
        this.semesterRepository.delete(this.semesterMapper.toSemester(semester));
    }
}
