/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.GradeType;
import java.util.List;

/**
 *
 * @author admin
 */
public interface GradeTypeRepository {
    List<GradeType> getGradeTypesByClassSubject(Integer classSubjectId);
    List<GradeType> getDefaultGradeTypes();
    GradeType getGradeTypeById(Integer id);
    void addOrUpdateGradeType(GradeType gradeType);
    void deleteGradeType(Integer id);
    long countGradeTypesByClassSubject(Integer classSubjectId);
    void initializeDefaultGradeTypes(Integer classSubjectId);
}
