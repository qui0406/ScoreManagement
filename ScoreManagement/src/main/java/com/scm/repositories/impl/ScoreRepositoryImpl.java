/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.impl;

import com.scm.pojo.*;
import com.scm.repositories.ScoreRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Set;

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
    private final String SCORE_TYPE_MIDDLE_TEST = "1";
    private final String SCORE_TYPE_FINAL_TEST = "2";

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addScore(Score score) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(score);
    }

    @Override
    public void updateScore(Score score) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(score);
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
    public boolean checkTestExisted(String classDetailId, String scoreTypeId, String studentId) {
        if (!scoreTypeId.equals(SCORE_TYPE_MIDDLE_TEST) && !scoreTypeId.equals(SCORE_TYPE_FINAL_TEST)) {
            return false;
        }
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Score> root = query.from(Score.class);

        query.select(builder.count(root));
        query.where(
            builder.equal(root.get("classDetails").get("id"), classDetailId),
            builder.equal(root.get("scoreType").get("id"), scoreTypeId),
            builder.equal(root.get("student").get("id"), studentId)
        );
        Long count = session.createQuery(query).getSingleResult();
        return count > 0;
    }

    @Override
    public boolean checkOver5TestExisted(String classDetailId, String scoreTypeId, String studentId) {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Score> root = query.from(Score.class);

        Predicate classSubjectPredicate = builder.equal(root.get("classDetails").get("id"), classDetailId);
        Predicate studentPredicate = builder.equal(root.get("student").get("id"), studentId);
        Predicate scoreTypeNotInPredicate = root.get("scoreType").get("id").in(1, 2).not();

        query.select(builder.count(root));
        query.where(builder.and(classSubjectPredicate, studentPredicate, scoreTypeNotInPredicate));
        Long count = session.createQuery(query).getSingleResult();
        return count < 5;
    }

    @Override
    public List<Score> getScoreSubjectByStudentId(Integer studentId, Integer classSubjectId ) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Score> q = b.createQuery(Score.class);
        Root<Score> root = q.from(Score.class);

        q.where(b.equal(root.get("student").get("id"), studentId),
                b.equal(root.get("classSubject").get("id"), classSubjectId));
        return  s.createQuery(q).getResultList();
    }

    @Override
    public void blockScore(String classDetailId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Integer> selectQuery = builder.createQuery(Integer.class);
        Root<Score> selectRoot = selectQuery.from(Score.class);
        selectQuery.select(selectRoot.get("id")).where(
                builder.equal(selectRoot.get("classDetails").get("id"), classDetailId)
        );

        List<Integer> scoreIds = session.createQuery(selectQuery).getResultList();

        if (!scoreIds.isEmpty()) {
            CriteriaUpdate<Score> update = builder.createCriteriaUpdate(Score.class);
            Root<Score> root = update.from(Score.class);
            update.set("active", false)
                    .where(root.get("id").in(scoreIds));
            session.createQuery(update).executeUpdate();
        }
    }

    @Override
    public void saveAll(Set<Score> scores) {
        Session session = factory.getObject().getCurrentSession();
        for (Score score : scores) {
            session.save(score);
        }
    }

    @Override
    public void save(Score score){
        Session session = factory.getObject().getCurrentSession();
        session.save(score);
    }

    @Override
    public List<Score> findScoreByStudentIdAndClassSubjectId(Integer studentId, Integer classSubjectId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> root = query.from(Score.class);
        query.select(root).where(
                builder.equal(root.get("student").get("id"), studentId),
                builder.equal(root.get("classSubject").get("id"), classSubjectId)
        );

        List<Score> scores = session.createQuery(query).getResultList();
        return scores;
    }


    @Override
    public Score getScoreByClassDetailIdAndStudentAndScoreType(String classDetailId, String studentId, String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> root = query.from(Score.class);
        query.select(root).where(
                builder.equal(root.get("student").get("id"), studentId),
                builder.equal(root.get("classDetails").get("id"), classDetailId),
                builder.equal(root.get("scoreType").get("id"), scoreTypeId)
        );
        try {
            return session.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public boolean getStatusBlock(String classDetailId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = builder.createQuery(Boolean.class);
        Root<Score> root = query.from(Score.class);
        query.select(root.get("active").as(Boolean.class));

        query.where(builder.equal(root.get("classDetails").get("id"), classDetailId));
        query.orderBy(builder.asc(root.get("id")));
        return session.createQuery(query).setMaxResults(1).uniqueResult();
    }
}