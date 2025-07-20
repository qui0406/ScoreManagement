/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.ScoreType;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Score;
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
    public List<ClassroomSubject> getClassroomSubjectsByTeacherId(String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ClassroomSubject> q = b.createQuery(ClassroomSubject.class);
        Root<ClassroomSubject> root = q.from(ClassroomSubject.class);

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
    public ClassroomSubject findClassroomSubjectById(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ClassroomSubject.class, classSubjectId);
    }

    @Override
    public ClassroomSubject create(ClassroomSubject classroomSubject) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.merge(classroomSubject);
    }


    @Override
    public boolean existClassSubjectRegister(ClassroomSubject classroomSubject) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ClassroomSubject> root = query.from(ClassroomSubject.class);

        query.select(builder.count(root));
        query.where(
            builder.equal(root.get("classroom").get("id"), classroomSubject.getClassroom().getId()),
            builder.equal(root.get("subject").get("id"), classroomSubject.getSubject().getId()),
            builder.equal(root.get("semester"), classroomSubject.getSemester()),
            builder.equal(root.get("teacher").get("id"), classroomSubject.getTeacher().getId())
        );

        Long count = session.createQuery(query).getSingleResult();
        return count > 0;
    }

    @Override
    public void delete(Integer classSubjectId, String userId) {
        Session session = factory.getObject().getCurrentSession();


        ClassroomSubject cs = findClassroomSubjectById(classSubjectId);
        log.info("Delete ClassroomSubject by id " + classSubjectId);
        if (cs == null) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }
        if (!cs.getStudent().getId().toString().equals(userId)) {
            log.info("khong co quyen xoa");
            throw new AppException(ErrorCode.UNAUTHORIZE);
        }
        log.info("loio");
        session.remove(cs);
    }
}