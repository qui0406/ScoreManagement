package com.scm.services.impl;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.SemesterResponse;
import com.scm.dto.responses.SubjectResponse;
import com.scm.mapper.SubjectMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import com.scm.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
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
    public SubjectResponse create(SubjectRequest subject) {
        return subjectMapper.toSubjectResponse(this.subjectRepository.create(subjectMapper.toSubject(subject)));
    }

    @Override
    public void delete(String id) {
        this.subjectRepository.delete(this.subjectRepository.findById(id));
    }

//    @Override
//    public List<ClassDetailsResponse> getAllSubjectsByStudent(String studentId, Map<String, String> params) {
//        List<ClassDetails> classDetailsList =
//    }


    @Override
    public List<ClassDetailsResponse> getAllSubjectsByStudent(String studentId, Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<SubjectResponse> getAllSubjectsBySemester(String studentId, String semesterId) {
        List<Subject> responses= this.subjectRepository.getSubjectsCurrentSemester(studentId, semesterId);
        List<SubjectResponse> subjectResponses = new ArrayList<>();

        for(Subject subject: responses){
            subjectResponses.add(subjectMapper.toSubjectResponse(subject));
        }
        return subjectResponses;
    }

    @Override
    public List<SubjectResponse> getAllSubjects(String page) {
        List<Subject> subjects = this.subjectRepository.getAllSubjects(page);
        List<SubjectResponse> subjectResponses = new ArrayList<>();
        for(Subject subject: subjects){
            subjectResponses.add(subjectMapper.toSubjectResponse(subject));
        }
        return subjectResponses;
    }
}
