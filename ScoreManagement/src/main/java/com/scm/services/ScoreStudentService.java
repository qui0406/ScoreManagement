package com.scm.services;

import com.scm.dto.responses.ScoreStudentResponse;

import java.util.List;
import java.util.Map;

public interface ScoreStudentService {
    ScoreStudentResponse getScoreByStudent(String studentId, String classSubjectId);

    List<ScoreStudentResponse> getScoreByClassSubject(String classSubjectId);

    List<ScoreStudentResponse> findScoreByStudentId(Map<String, String> params, String classSubjectId);
}
