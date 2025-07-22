/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Score;
import java.util.List;
import java.util.Set;

/**
 *
 * @author admin
 */
public interface ScoreRepository {
    void addOrUpdateScore(Score grade, String teacherId);
    List<Score> getScoresByClassSubjectId(Integer classSubjectId);
    Score getGradeByStudentAndClassSubjectAndType(Long studentId, Integer classSubjectId, Integer gradeTypeId);
    Score findScoreById(Integer id);

    boolean checkTestExsit(Integer classSubjectId, Integer scoreTypeId, Integer studentId);
    boolean checkOver5TestExsit(Integer classSubjectId, Integer scoreTypeId, Integer studentId);

    List<Score> getScoreSubjectByStudentId(Integer studentId,  Integer subjectId);

    void closeScore(Integer teacherId, Integer classroomId);

    void saveAll(Set<Score> scores);
    void save(Score score);

    List<Score> findScoreByStudentIdAndClassSubjectId(Integer studentId, Integer classSubjectId);
}