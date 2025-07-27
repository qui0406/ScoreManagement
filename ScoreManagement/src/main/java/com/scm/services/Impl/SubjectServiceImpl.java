package com.scm.services.Impl;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import com.scm.services.SemesterService;
import com.scm.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<SubjectResponse> getAllSubjectsByStudent(String studentId, Map<String, String> params) {
        List<Subject> subjects = this.subjectRepository.getAllSubjectsByStudentId(studentId, params);
        List<SubjectResponse> subjectResponses = new ArrayList<>();
        for(Subject subject: subjects){
            subjectResponses.add(subjectMapper.toSubjectResponse(subject));
        }
        return subjectResponses;
    }
}
