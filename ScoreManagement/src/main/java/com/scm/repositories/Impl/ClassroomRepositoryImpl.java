/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.ClassDetails;
import com.scm.pojo.Classroom;
import com.scm.pojo.Student;
import com.scm.repositories.ClassroomRepository;
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
public class ClassroomRepositoryImpl implements ClassroomRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public String getIdByClassName(String className) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Integer> c = b.createQuery(Integer.class);
        Root<Classroom> root = c.from(Classroom.class);
        c.select(root.get("id"))
                .where(b.equal(root.get("name"), className));

        Integer result = s.createQuery(c).uniqueResult(); // chạy query
        return result != null ? result.toString() : null;
    }

    @Override
    public List<Classroom> getListClassRoom() {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Classroom> criteria = builder.createQuery(Classroom.class);
        Root<Classroom> root = criteria.from(Classroom.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    @Override
    public Classroom findById(String classroomId) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Classroom.class, classroomId);
    }

    @Override
    public List<Student> getStudentsInClassroom(String classroomId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        query.select(root)
                .where(builder.equal(root.get("classroom").get("id"), classroomId));

        return session.createQuery(query).getResultList();
    }

    @Override
    public Classroom create(Classroom classroom) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(classroom);
        return classroom;
    }

    @Override
    public void delete(Classroom classroom) {
        Session session = factory.getObject().getCurrentSession();
        session.remove(classroom);
    }
}