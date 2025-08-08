package com.scm.repositories;

import com.scm.dto.responses.ScoreResponse;
import com.scm.pojo.Score;
import com.scm.pojo.Student;

import java.util.List;
import java.util.Map;

public interface ScoreStudentRepository {
    List<Score> getScoresByStudentAndClass(String studentId, String classDetailId);
    List<Score> getScoresByStudentAndClassWhenBlockScore(String studentId, String classDetailId);
    List<Student> findScoreStudentByMSSVOrName(Map<String,String> params, String classDetailId);
}
