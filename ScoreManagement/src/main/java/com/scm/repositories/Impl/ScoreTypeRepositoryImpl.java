/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.ScoreType;
import com.scm.repositories.ScoreTypeRepository;
import jakarta.persistence.Query;

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
public class ScoreTypeRepositoryImpl implements ScoreTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ScoreType> getScoreTypesByClassSubject(Integer classSubjectId) {

        return null;
    }

    @Override
    public List<ScoreType> getDefaultScoreTypes() {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "FROM ScoreType gt WHERE gt.scoreTypeName IN ('Điểm giữa kỳ', 'Điểm cuối kỳ') ORDER BY gt.id";
        Query query = s.createQuery(hql, ScoreType.class);
        return query.getResultList();
    }

//    @Override
//    public List<GradeType> getAllGradeTypes() {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = s.getCriteriaBuilder();
//        CriteriaQuery<GradeType> q = b.createQuery(GradeType.class);
//        Root<GradeType> root = q.from(GradeType.class);
//
//        q.select(root);
//        q.orderBy(b.asc(root.get("id")));
//
//        Query query = s.createQuery(q);
//        return query.getResultList();
//    }

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