/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.Classroom;
import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.Teacher;
import com.scm.repositories.ClassroomRepository;
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
public class ClassroomRepositoryImpl implements ClassroomRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Classroom> getClassroomsByTeacherId(String teacherId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Classroom> q = b.createQuery(Classroom.class);
        Root<ClassroomSubject> root = q.from(ClassroomSubject.class);

        q.select(root.get("class_id"));
        q.where(b.equal(root.get("teacher").get("user").get("id"), teacherId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }
}
