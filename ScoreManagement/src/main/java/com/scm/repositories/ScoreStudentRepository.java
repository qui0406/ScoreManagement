package com.scm.repositories;

import com.scm.dto.responses.ScoreResponse;
import com.scm.pojo.Score;
import com.scm.pojo.Student;

import java.util.List;
import java.util.Map;

public interface ScoreStudentRepository {
    Score getScoreByStudent(String studentId, String classSubjectId);

    List<Score> getAllScoreByStudentAndClassSubject(String studentId, String classSubjectId);

    List<Student> getAllStudentsInClassSubject(String classSubjectId);

    List<Student> findScoreStudentByMSSVOrName(Map<String,String> pamams);
}
