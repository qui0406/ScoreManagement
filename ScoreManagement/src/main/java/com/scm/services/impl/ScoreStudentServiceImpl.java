package com.scm.services.impl;

import com.scm.dto.*;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.dto.responses.WriteScoreStudentPDFResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ScoreStudentMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Score;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Student;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ScoreStudentRepository;
import com.scm.repositories.StudentRepository;
import com.scm.services.RedisService;
import com.scm.services.ScoreService;
import com.scm.services.ScoreStudentService;
import com.scm.services.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ScoreStudentServiceImpl implements ScoreStudentService {
    @Autowired
    private ScoreStudentRepository scoreStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public ScoreStudentResponse getScoreByStudentAndClass(String studentId, String classDetailId) {
        List<Score> scores = scoreStudentRepository.getScoresByStudentAndClass(studentId, classDetailId);
        Student student= studentRepository.findById(studentId);
        return toScoreResponseList(scores, student);
    }


    private WriteScoreStudentPDFResponse scorePDF(String studentId, String classDetailId) {
        List<Score> scores = scoreStudentRepository.getScoresByStudentAndClass(studentId, classDetailId);
        return toWriteScoreStudentPDFResponse(scores);
    }

    @Override
    public List<ScoreStudentResponse> getScoreByClassDetails(String classDetailId, String teacherId) {
//        String cacheKey = "classScore:" + classDetailId + "_" + teacherId;
//        Object cached = redisService.getValue(cacheKey);
//        if (cached != null) {
//            return (List<ScoreStudentResponse>) cached;
//        }

        ClassDetails classDetails = this.classDetailsRepository.findById(classDetailId);
        if(!classDetails.getTeacher().getId().toString().equals(teacherId)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        List<Student> students = this.studentRepository.getAllStudentsByClass(classDetailId);
        List<ScoreStudentResponse> scoreStudentResponse = new ArrayList<>();

        for (Student student : students) {
            ScoreStudentResponse response = getScoreByStudentAndClass(student.getId().toString(), classDetailId);
            scoreStudentResponse.add(response);
        }



//        redisService.setValue(cacheKey, scoreStudentResponse);
        return scoreStudentResponse;
    }


    @Override
    public List<WriteScoreStudentPDFResponse> listScorePDF(String classDetailId, String teacherId) {
        List<Student> students = this.studentRepository.getAllStudentsByClass(classDetailId);
        List<WriteScoreStudentPDFResponse> pdfResponses = new ArrayList<>();

        for (Student student : students) {
            WriteScoreStudentPDFResponse response = scorePDF(student.getId().toString(), classDetailId);
            pdfResponses.add(response);
        }
        return pdfResponses;
    }

    @Override
    public List<ScoreStudentResponse> findScoreByStudentId(Map<String, String> params, String classDetailId) {
        List<Student> students = scoreStudentRepository.findScoreStudentByMSSVOrName(params, classDetailId);
        List<ScoreStudentResponse> scoreStudentResponse = new ArrayList<>();

        for (Student student : students) {
            ScoreStudentResponse response = getScoreByStudentAndClass(student.getId().toString(), classDetailId);
            scoreStudentResponse.add(response);
        }
        return scoreStudentResponse;
    }

    @Override
    public List<ScoreType> getScoreTypes() {
        return null;
    }

    private ScoreStudentResponse toScoreResponseList(List<Score> scores, Student student) {
        if (scores == null || scores.isEmpty()) {
            ScoreStudentResponse emptyResponse = new ScoreStudentResponse();
            emptyResponse.setStudent(new StudentDTO(
                    student.getId().toString(),
                    student.getMssv(),
                    student.getLastName() + " " + student.getFirstName()
            ));
            emptyResponse.setScores(new ArrayList<>());
            return emptyResponse;
        }

        Score score = scores.get(0);
        ClassDetails classDetails = score.getClassDetails();
        TeacherDTO teacherDTO = teacherService.getTeacherDTOById(score.getClassDetails().getTeacher().getId().toString());
        Map<Integer, ScoreByTypeDTO> scoresMap = scoreService.getGroupedScores(scores);

        ScoreStudentResponse response = new ScoreStudentResponse();
        response.setStudent(new StudentDTO(
                student.getId().toString(),
                student.getMssv(),
                student.getLastName() + " " + student.getFirstName()
        ));

        response.setSubject(new SubjectDTO(
                classDetails.getSubject().getId(),
                classDetails.getSubject().getSubjectName()
        ));

        response.setClassroom(new ClassroomDTO(
                classDetails.getClassroom().getId(),
                classDetails.getClassroom().getName()
        ));

        response.setTeacher(teacherDTO);
        response.setScores(new ArrayList<>(scoresMap.values()));
        return response;
    }

    private WriteScoreStudentPDFResponse toWriteScoreStudentPDFResponse(List<Score> scores) {
        Score score = scores.get(0);

        Student student = score.getStudent();
        ClassDetails classDetails = score.getClassDetails();

        WriteScoreStudentPDFResponse response = new WriteScoreStudentPDFResponse();

        response.setFullName(student.getLastName() + " " + student.getFirstName());
        response.setMssv(student.getMssv());
        response.setClassName(classDetails.getClassroom().getName());

        Map<Integer, BigDecimal> scoreMap = new LinkedHashMap<>();

        for (Score s : scores) {
            Integer scoreTypeId = s.getScoreType().getId();
            BigDecimal scoreValue = s.getScore();
            scoreMap.put(scoreTypeId, scoreValue);
        }
        response.setScores(scoreMap);
        return response;
    }
}
