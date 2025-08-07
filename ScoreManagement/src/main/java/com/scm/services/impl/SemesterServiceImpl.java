package com.scm.services.impl;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.SemesterResponse;
import com.scm.mapper.SemesterMapper;
import com.scm.pojo.Semester;
import com.scm.repositories.SemesterRepository;
import com.scm.services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public SemesterResponse create(SemesterRequest semester) {
        Semester s = semesterMapper.toSemester(semester);
        return semesterMapper.toSemesterResponse(this.semesterRepository.create(s));
    }

    @Override
    public void delete(SemesterRequest semester) {
        this.semesterRepository.delete(this.semesterMapper.toSemester(semester));
    }

    @Override
    public List<SemesterResponse> getAllSemesters() {
        List<Semester> semesters = this.semesterRepository.getAllSemesters();
        List<SemesterResponse> semesterResponses = new ArrayList<>();
        for (Semester semester : semesters) {
            SemesterResponse semesterResponse = this.semesterMapper.toSemesterResponse(semester);
            semesterResponses.add(semesterResponse);
        }
        return semesterResponses;
    }
}
