package com.scm.services.Impl;

import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.mapper.ScoreStudentMapper;
import com.scm.pojo.Score;
import com.scm.pojo.Student;
import com.scm.repositories.ClassroomSubjectRepository;
import com.scm.repositories.ScoreStudentRepository;
import com.scm.repositories.StudentRepository;
import com.scm.services.ScoreStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreStudentServiceImpl implements ScoreStudentService {
    @Autowired
    private ScoreStudentMapper scoreStudentMapper;
    @Autowired
    private ScoreStudentRepository scoreStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassroomSubjectRepository classSubjectRepository;

    @Override
    public ScoreStudentResponse getScoreByStudent(String studentId, String classSubjectId) {
        Score score = scoreStudentRepository.getScoreByStudent(studentId, classSubjectId);
        return scoreStudentMapper.toScoreResponse(score);
    }

    @Override
    public List<ScoreStudentResponse> getScoreByClassSubject(String classSubjectId) {
        List<Student> students = scoreStudentRepository.getAllStudentsInClassSubject(classSubjectId);
        List<ScoreStudentResponse> scoreStudentRespons = new ArrayList<>();

        for (Student student : students) {
            Score score = scoreStudentRepository.getScoreByStudent(student.getId().toString(), classSubjectId);
            ScoreStudentResponse response = scoreStudentMapper.toScoreResponse(score);
            scoreStudentRespons.add(response);
        }
        return scoreStudentRespons;
    }

    @Override
    public List<ScoreStudentResponse> findScoreByStudentId(Map<String, String> params, String classSubjectId) {
        List<Student> students = scoreStudentRepository.findScoreStudentByMSSVOrName(params);
        List<ScoreStudentResponse> scoreStudentRespons = new ArrayList<>();

        for (Student student : students) {
            Score score = scoreStudentRepository.getScoreByStudent(student.getId().toString(), classSubjectId);
            ScoreStudentResponse response = scoreStudentMapper.toScoreResponse(score);
            scoreStudentRespons.add(response);
        }
        return scoreStudentRespons;
    }
}
