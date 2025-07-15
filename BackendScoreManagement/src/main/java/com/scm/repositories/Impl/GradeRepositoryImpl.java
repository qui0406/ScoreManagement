/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.Grade;
import com.scm.repositories.GradeRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class GradeRepositoryImpl implements GradeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdateGrade(Grade grade) {
        Session s = this.factory.getObject().getCurrentSession();
        if (grade.getId() == null) {
            s.persist(grade);
        } else {
            s.merge(grade);
        }
    }

    @Override
    public List<Grade> getGradesByClassSubjectId(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Grade> q = b.createQuery(Grade.class);
        Root<Grade> root = q.from(Grade.class);

        q.select(root);
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Grade getGradeByStudentAndClassSubjectAndType(Long studentId, Integer classSubjectId, Integer gradeTypeId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Grade> q = b.createQuery(Grade.class);
        Root<Grade> root = q.from(Grade.class);

        q.select(root);
        q.where(
            b.and(
                b.equal(root.get("student").get("id"), studentId),
                b.equal(root.get("classSubject").get("id"), classSubjectId),
                b.equal(root.get("gradeType").get("id"), gradeTypeId)
            )
        );

        Query query = s.createQuery(q);
        List<Grade> grades = query.getResultList();
        return grades.isEmpty() ? null : grades.get(0);
    }
}
