/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.*;
import com.scm.repositories.StudentRepository;
import jakarta.persistence.NoResultException;
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
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<String> query = b.createQuery(String.class);
        Root<Student> root = query.from(Student.class);

        query.select(root.get("id"))
                .where(b.equal(root.get("username"), username));

        return s.createQuery(query).uniqueResult();
    }

    @Override
    public List<Student> getAllStudentsByClass(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        log.info("Truy vấn database");

        CriteriaQuery<Student> query = b.createQuery(Student.class);
        Root<EnrollDetails> root = query.from(EnrollDetails.class);
        Join<EnrollDetails, Student> studentJoin = root.join("student");
        query.select(studentJoin)
                .where(b.equal(root.get("classDetails").get("id"), classDetailId));
        return session.createQuery(query).getResultList();
    }

    @Override
    public Student getIdByMssv(String mssv) {
        if (mssv == null || mssv.trim().isEmpty()) {
            return null;
        }
        try {
            Session session = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = builder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root)
                    .where(builder.equal(root.get("mssv"), mssv));

            return session.createQuery(query).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAllMssvByClass(String classDetailId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();

        CriteriaQuery<String> query = b.createQuery(String.class);
        Root<EnrollDetails> root = query.from(EnrollDetails.class);

        Join<EnrollDetails, Student> studentJoin = root.join("student");
        Join<EnrollDetails, ClassDetails> classJoin = root.join("classDetails");

        query.select(studentJoin.get("mssv"))
                .where(b.equal(classJoin.get("id"), classDetailId));

        return session.createQuery(query).getResultList();
    }
}