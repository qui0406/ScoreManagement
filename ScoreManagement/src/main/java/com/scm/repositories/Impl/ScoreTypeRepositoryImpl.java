/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.*;
import com.scm.repositories.ClassSubjectScoreRepository;
import com.scm.repositories.ScoreTypeRepository;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
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
public class ScoreTypeRepositoryImpl implements ScoreTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private ClassSubjectScoreRepository classSubjectScoreRepository;

    @Override
    public List<ScoreType> getScoreTypesByClassSubject(String classSubjectId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ScoreType> query = builder.createQuery(ScoreType.class);
        Root<ClassSubjectScore> root = query.from(ClassSubjectScore.class);
        Join<ClassSubjectScore, ScoreType> scoreTypeJoin = root.join("scoreType");
        query.select(scoreTypeJoin).distinct(true)
                .where(builder.equal(root.get("classSubject").get("id"), classSubjectId));
        List<ScoreType> scoreTypes = session.createQuery(query).getResultList();
        return scoreTypes;
    }

    @Override
    public void addScoreType(String classSubjectId, String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();

        if (!checkScoreTypeExistedClassSubject(classSubjectId, scoreTypeId)) {
            ClassSubject classSubject = session.get(ClassSubject.class, classSubjectId);
            ScoreType scoreType = session.get(ScoreType.class, scoreTypeId);

            ClassSubjectScore classSubjectScore = new ClassSubjectScore();
            classSubjectScore.setClassSubject(classSubject);
            classSubjectScore.setScoreType(scoreType);

            classSubjectScoreRepository.save(classSubjectScore);
        }else {
            throw new AppException(ErrorCode.SCORE_TYPE_EXISTED);
        }
    }

    @Override
    public void deleteScoreType(String classSubjectId, String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();
        if (checkScoreTypeExistedClassSubject(classSubjectId, scoreTypeId)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ClassSubjectScore> query = builder.createQuery(ClassSubjectScore.class);
            Root<ClassSubjectScore> root = query.from(ClassSubjectScore.class);

            query.select(root).where(
                    builder.equal(root.get("classSubject").get("id"), classSubjectId),
                    builder.equal(root.get("scoreType").get("id"), scoreTypeId)
            );

            ClassSubjectScore classSubjectScore = session.createQuery(query).uniqueResult();
            classSubjectScoreRepository.delete(classSubjectScore);
        }else {
            throw new AppException(ErrorCode.SCORE_TYPE_EXISTED);
        }
    }


    private boolean checkScoreTypeExistedClassSubject(String classSubjectId, String scoreTypeId) {
        List<ScoreType> scoreTypes = getScoreTypesByClassSubject(classSubjectId);
        ScoreType addScoreType = getScoreTypeById(scoreTypeId);
        if (scoreTypes.contains(addScoreType)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ScoreType getScoreTypeById(String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();
        return  session.get(ScoreType.class, scoreTypeId);
    }

    @Override
    public List<ScoreType> getScoreTypes() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ScoreType> query = builder.createQuery(ScoreType.class);
        Root<ScoreType> root = query.from(ScoreType.class);
        query.select(root).where(
                builder.not(root.get("id").in("1", "2"))
        );

        return session.createQuery(query).getResultList();
    }

    @Override
    public List<ScoreType> getDefaultScoreTypes() {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "FROM ScoreType gt WHERE gt.scoreTypeName IN ('Điểm giữa kỳ', 'Điểm cuối kỳ') ORDER BY gt.id";
        Query query = s.createQuery(hql, ScoreType.class);
        return query.getResultList();
    }


    @Override
    public ScoreType findScoreTypeById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ScoreType.class, id);
    }

    @Override
    public void addOrUpdateGradeType(ScoreType scoreType) {
        Session s = this.factory.getObject().getCurrentSession();
        if (scoreType.getId() == null) {
            s.persist(scoreType);
        } else {
            s.merge(scoreType);
        }
    }

    @Override
    public void deleteGradeType(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        ScoreType scoreType = this.findScoreTypeById(id);
        if (scoreType != null) {
            s.remove(scoreType);
        }
    }

    @Override
    public long countGradeTypesByClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();


        return 1;
    }


}