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
    void addScore(Score score);
    void updateScore(Score score);
    List<Score> getScoresByClassSubjectId(Integer classSubjectId);

    boolean checkTestExisted(String classDetailId, String scoreTypeId, String studentId);
    boolean checkOver5TestExisted(String classDetailId, String scoreTypeId, String studentId);

    List<Score> getScoreSubjectByStudentId(Integer studentId,  Integer subjectId);

    void blockScore(String classSubjectId);

    void saveAll(Set<Score> scores);
    void save(Score score);

    boolean getStatusBlock(String classDetailId);

    List<Score> findScoreByStudentIdAndClassSubjectId(Integer studentId, Integer classSubjectId);
    Score getScoreByClassDetailIdAndStudentAndScoreType(String classDetailId, String studentId, String scoreTypeId);
}