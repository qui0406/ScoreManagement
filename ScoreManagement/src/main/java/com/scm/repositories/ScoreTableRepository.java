package com.scm.repositories;

import com.scm.pojo.Score;

import java.util.List;

public interface ScoreTableRepository {
    List<Score> getAllStudentsInClass(String classroomSubjectId, String teacherId);
}
