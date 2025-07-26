/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.*;
import com.scm.repositories.ClassroomSubjectRepository;
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
public class ClassroomSubjectRepositoryImpl implements ClassroomSubjectRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ClassSubject> getClassroomSubjectsByTeacherId(String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ClassSubject> q = b.createQuery(ClassSubject.class);
        Root<ClassSubject> root = q.from(ClassSubject.class);

        q.select(root);
        q.where(b.equal(root.get("teacher").get("id"), teacherId));
//        q.orderBy(b.asc(root.get("classField").get("name")));

        Query query = s.createQuery(q);
        return query.getResultList();
    }


    @Override
    public int countStudentsInClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<StudentEnrollment> root = q.from(StudentEnrollment.class);

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
    public ClassSubject findClassroomSubjectById(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ClassSubject.class, classSubjectId);
    }

    @Override
    public ClassSubject create(ClassSubject classSubject) {
        Session session = this.factory.getObject().getCurrentSession();
        ClassSubject saved = session.merge(classSubject);
        session.flush();

        ScoreType middleScore = session.get(ScoreType.class, "1");
        ScoreType finalScore = session.get(ScoreType.class, "2");

        if (middleScore != null && finalScore != null) {
            ClassSubjectScore css1 = new ClassSubjectScore();
            css1.setClassSubject(saved);
            css1.setScoreType(middleScore);

            ClassSubjectScore css2 = new ClassSubjectScore();
            css2.setClassSubject(saved);
            css2.setScoreType(finalScore);

            session.persist(css1);
            session.persist(css2);
        }
        return saved;
    }


    @Override
    public boolean existClassSubjectRegister(ClassSubject classSubject) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ClassSubject> root = query.from(ClassSubject.class);

        query.select(builder.count(root));
        query.where(
            builder.equal(root.get("classroom").get("id"), classSubject.getClassroom().getId()),
            builder.equal(root.get("subject").get("id"), classSubject.getSubject().getId()),
            builder.equal(root.get("semester"), classSubject.getSemester()),
            builder.equal(root.get("teacher").get("id"), classSubject.getTeacher().getId())
        );
        Long count = session.createQuery(query).getSingleResult();
        return count > 0;
    }

    @Override
    public void delete(Integer classSubjectId, String userId) {
        Session session = factory.getObject().getCurrentSession();


        ClassSubject cs = findClassroomSubjectById(classSubjectId);
        log.info("Delete ClassroomSubject by id " + classSubjectId);
        if (cs == null) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }

        log.info("loio");
        session.remove(cs);
    }

    @Override
    public ClassSubject getScoreSubjectByStudentId(String id, String classroomSubjectId, String teacherId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClassSubject> query = builder.createQuery(ClassSubject.class);
        Root<ClassSubject> root = query.from(ClassSubject.class);

        query.select(root).where(
                builder.equal(root.get("student").get("id"), id),
                builder.equal(root.get("id"), classroomSubjectId),
                builder.equal(root.get("teacher").get("id"), teacherId),
                builder.equal(root.get("semester").get("id"), semesterId)
        );

        ClassSubject result = session.createQuery(query).getSingleResult();
        return result;
    }

    @Override
    public List<ClassSubject> getAllStudentsInClass(String classroomSubjectId, String teacherId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClassSubject> query = builder.createQuery(ClassSubject.class);
        Root<ClassSubject> root = query.from(ClassSubject.class);

        query.select(root).where(
                builder.equal(root.get("id"), classroomSubjectId),
                builder.equal(root.get("teacher").get("id"), teacherId),
                builder.equal(root.get("semester").get("id"), semesterId)
        );

        List<ClassSubject> responses = session.createQuery(query).getResultList();
        return responses;
    }
}