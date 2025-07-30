package com.scm.services;

import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.dto.responses.WriteScoreStudentPDFResponse;
import com.scm.pojo.ScoreType;

import java.util.List;
import java.util.Map;

public interface ScoreStudentService {
    ScoreStudentResponse getScoreByStudentAndClass(String studentId, String classDetailId);

    List<ScoreStudentResponse> getScoreByClassDetails(String classDetailId, String teacherId);

    List<ScoreStudentResponse> findScoreByStudentId(Map<String, String> params, String classDetailId);

    List<ScoreType> getScoreTypes();

    List<WriteScoreStudentPDFResponse> listScorePDF(String classDetailId, String teacherId);
}
