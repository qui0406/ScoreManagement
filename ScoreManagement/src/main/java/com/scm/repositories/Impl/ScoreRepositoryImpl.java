/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.*;
import com.scm.repositories.ScoreRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ScoreRepositoryImpl implements ScoreRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdateScore(Score score, String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();
        if (score.getId() == null) {
            s.persist(score);
        } else {
            s.merge(score);
        }
    }

    @Override
    public List<Score> getScoresByClassSubjectId(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Score> q = b.createQuery(Score.class);
        Root<Score> root = q.from(Score.class);
        q.select(root);
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));
        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Score getGradeByStudentAndClassSubjectAndType(Long studentId, Integer classSubjectId, Integer gradeTypeId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Score> q = b.createQuery(Score.class);
        Root<Score> root = q.from(Score.class);

        q.select(root);
        q.where(
                b.and(
                        b.equal(root.get("student").get("id"), studentId),
                        b.equal(root.get("classSubject").get("id"), classSubjectId),
                        b.equal(root.get("gradeType").get("id"), gradeTypeId)
                )
        );

        Query query = s.createQuery(q);
        List<Score> grades = query.getResultList();
        return grades.isEmpty() ? null : grades.get(0);
    }

    @Override
    public Score findScoreById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Score.class, id);
    }
}