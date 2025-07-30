/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.*;
import com.scm.repositories.StudentRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;

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
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Student findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Student.class, id);
    }

    @Override
    public String findIdByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Student> root = cq.from(Student.class);

        cq.select(root.get("id"))
                .where(cb.equal(root.get("username"), username));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public List<Student> getAllStudentsByClass(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<EnrollDetails> root = cq.from(EnrollDetails.class);
        Join<EnrollDetails, Student> studentJoin = root.join("student");
        cq.select(studentJoin)
                .where(cb.equal(root.get("classDetails").get("id"), classDetailId));

        List<Student> students = session.createQuery(cq).list();
        for (Student student : students) {
            log.info(student.getUsername());
        }
        return session.createQuery(cq).getResultList();
    }

    @Override
    public String getIdByMssv(String mssv) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Integer> c = b.createQuery(Integer.class);
        Root<Student> root = c.from(Student.class);
        c.select(root.get("id"))
                .where(b.equal(root.get("mssv"), mssv));

        Integer result = s.createQuery(c).uniqueResult();
        return result != null ? result.toString() : null;
    }

    @Override
    public List<String> getAllMssvByClass(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<EnrollDetails> root = cq.from(EnrollDetails.class);

        Join<EnrollDetails, Student> studentJoin = root.join("student");
        Join<EnrollDetails, ClassDetails> classJoin = root.join("classDetails");

        cq.select(studentJoin.get("mssv"))
                .where(cb.equal(classJoin.get("id"), classDetailId));

        log.info("jiuhygvuhub: {}", session.createQuery(cq).getResultList().toString());

        return session.createQuery(cq).getResultList();
    }
}