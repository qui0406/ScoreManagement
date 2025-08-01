/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.*;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.ScoreTypeRepository;
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
public class ClassDetailsRepositoryImpl implements ClassDetailsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private ScoreTypeRepository scoreTypeRepository;

    @Override
    public List<ClassDetails> getClassroomSubjectsByTeacherId(String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ClassDetails> q = b.createQuery(ClassDetails.class);
        Root<ClassDetails> root = q.from(ClassDetails.class);

        q.select(root);
        q.where(b.equal(root.get("teacher").get("id"), teacherId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<ClassDetails> getClassroomsByTeacherId(String teacherId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<ClassDetails> q = b.createQuery(ClassDetails.class);
            Root<ClassDetails> root = q.from(ClassDetails.class);

            q.where(b.equal(root.get("teacher").get("id"), teacherId));
            return s.createQuery(q).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public int countStudentsInClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<EnrollDetails> root = q.from(EnrollDetails.class);

        q.select(b.count(root));
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));

        Long result = s.createQuery(q).getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public int countScoreTypesInClassSubject(Integer classSubjectId) {
        if (classSubjectId == null) return 0;

        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Score> root = query.from(Score.class);

        query.select(cb.countDistinct(root.get("scoreType")));
        query.where(cb.equal(root.get("classSubject").get("id"), classSubjectId));

        Long result = session.createQuery(query).getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public ClassDetails findById(String classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ClassDetails.class, classSubjectId);
    }


    @Override
    public void create(ClassDetails classDetails) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(classDetails);

        ScoreType scoreType1 = session.find(ScoreType.class, 1);
        ScoreType scoreType2 = session.find(ScoreType.class, 2);

        if (scoreType1 != null) {
            ClassDetailsScoreType cs1 = new ClassDetailsScoreType();
            cs1.setClassDetails(classDetails);
            cs1.setScoreType(scoreType1);
            session.persist(cs1);
        }

        if (scoreType2 != null) {
            ClassDetailsScoreType cs2 = new ClassDetailsScoreType();
            cs2.setClassDetails(classDetails);
            cs2.setScoreType(scoreType2);
            session.persist(cs2);
        }
    }


    @Override
    public boolean existClassSubjectRegister(ClassDetails classDetails) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ClassDetails> root = query.from(ClassDetails.class);

        query.select(builder.count(root));
        query.where(
            builder.equal(root.get("classroom").get("id"), classDetails.getClassroom().getId()),
            builder.equal(root.get("subject").get("id"), classDetails.getSubject().getId()),
            builder.equal(root.get("teacher").get("id"), classDetails.getTeacher().getId())
        );
        Long count = session.createQuery(query).getSingleResult();
        return count > 0;
    }

    @Override
    public void delete(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();


        ClassDetails cs = findById(classDetailId);
        log.info("Delete ClassroomSubject by id " + classDetailId);
        if (cs == null) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }

        log.info("loio");
        session.remove(cs);
    }

    @Override
    public ClassDetails getScoreSubjectByStudentId(String id, String classroomSubjectId, String teacherId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClassDetails> query = builder.createQuery(ClassDetails.class);
        Root<ClassDetails> root = query.from(ClassDetails.class);

        query.select(root).where(
                builder.equal(root.get("student").get("id"), id),
                builder.equal(root.get("id"), classroomSubjectId),
                builder.equal(root.get("teacher").get("id"), teacherId),
                builder.equal(root.get("semester").get("id"), semesterId)
        );

        ClassDetails result = session.createQuery(query).getSingleResult();
        return result;
    }

    @Override
    public List<ClassDetails> getAllStudentsInClass(String classroomSubjectId, String teacherId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClassDetails> query = builder.createQuery(ClassDetails.class);
        Root<ClassDetails> root = query.from(ClassDetails.class);

        query.select(root).where(
                builder.equal(root.get("id"), classroomSubjectId),
                builder.equal(root.get("teacher").get("id"), teacherId),
                builder.equal(root.get("semester").get("id"), semesterId)
        );

        List<ClassDetails> responses = session.createQuery(query).getResultList();
        return responses;
    }

}