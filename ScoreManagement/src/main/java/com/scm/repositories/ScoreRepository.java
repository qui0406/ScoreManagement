/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.Score;
import java.util.List;

/**
 *
 * @author admin
 */
public interface ScoreRepository {
    void addOrUpdateScore(Score grade, String teacherId);
    List<Score> getScoresByClassSubjectId(Integer classSubjectId);
    Score getGradeByStudentAndClassSubjectAndType(Long studentId, Integer classSubjectId, Integer gradeTypeId);

    Score findScoreById(Integer id);
}