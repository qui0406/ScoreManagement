/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.*;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ScoreTypeRepository;
import jakarta.persistence.Query;

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
    private ClassDetailsRepository classDetailsRepository;

    @Override
    public List<ScoreType> getScoreTypesByClassDetails(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ScoreType> query = builder.createQuery(ScoreType.class);
        Root<ClassDetailsScoreType> root = query.from(ClassDetailsScoreType.class);
        Join<ClassDetailsScoreType, ScoreType> scoreTypeJoin = root.join("scoreType");
        query.select(scoreTypeJoin).distinct(true)
                .where(builder.equal(root.get("classDetails").get("id"), classDetailId));
        List<ScoreType> scoreTypes = session.createQuery(query).getResultList();
        return scoreTypes;
    }

    @Override
    public void addScoreType(String classDetailId, String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();

        if (!checkScoreTypeExistedClassSubject(classDetailId, scoreTypeId)) {
            ClassDetails classDetails = session.get(ClassDetails.class, classDetailId);
            ScoreType scoreType = session.get(ScoreType.class, scoreTypeId);

            ClassDetailsScoreType classDetailsScoreType = new ClassDetailsScoreType();
            classDetailsScoreType.setClassDetails(classDetails);
            classDetailsScoreType.setScoreType(scoreType);

            session.persist(classDetailsScoreType);
        } else {
            throw new AppException(ErrorCode.SCORE_TYPE_EXISTED);
        }
    }

    @Override
    public void deleteScoreType(String classDetailId, String scoreTypeId) {
        Session session = factory.getObject().getCurrentSession();

        if (checkScoreTypeExistedClassSubject(classDetailId, scoreTypeId)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ClassDetailsScoreType> query = builder.createQuery(ClassDetailsScoreType.class);
            Root<ClassDetailsScoreType> root = query.from(ClassDetailsScoreType.class);

            query.select(root).where(
                    builder.equal(root.get("classDetails").get("id"), classDetailId),
                    builder.equal(root.get("scoreType").get("id"), scoreTypeId)
            );

            ClassDetailsScoreType classDetailsScoreType = session.createQuery(query).uniqueResult();

            if (classDetailsScoreType != null)
                session.remove(classDetailsScoreType);
        } else {
            throw new AppException(ErrorCode.INVALID_DATA);
        }
    }


    private boolean checkScoreTypeExistedClassSubject(String classDetailId, String scoreTypeId) {
        List<ScoreType> scoreTypes = getScoreTypesByClassDetails(classDetailId);
        ScoreType addScoreType = findById(scoreTypeId);
        if (scoreTypes.contains(addScoreType)) {
            return true;
        }else {
            return false;
        }
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
    public ScoreType findById(String id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ScoreType.class, id);
    }

}