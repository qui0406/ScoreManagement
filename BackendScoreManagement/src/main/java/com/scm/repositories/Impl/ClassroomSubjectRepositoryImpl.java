/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Grade;
import com.scm.repositories.ClassroomSubjectRepository;
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
        q.where(b.equal(root.get("teacher").get("user").get("id"), teacherId));
        q.orderBy(b.asc(root.get("classField").get("name")));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public ClassroomSubject getClassroomSubjectById(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ClassroomSubject.class, classSubjectId);
    }

    @Override
    public int countStudentsInClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<StudentEnrollment> root = q.from(StudentEnrollment.class);

        q.select(b.count(root.get("student")));
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));

        Query query = s.createQuery(q);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public int countGradeTypesInClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Grade> root = q.from(Grade.class);

        q.select(b.countDistinct(root.get("gradeType")));
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));

        Query query = s.createQuery(q);
        return ((Long) query.getSingleResult()).intValue();
    }
}
