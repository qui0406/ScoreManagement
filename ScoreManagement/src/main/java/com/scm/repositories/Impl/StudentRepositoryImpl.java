/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.ScoreType;
import com.scm.pojo.Student;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Teacher;
import com.scm.repositories.StudentRepository;
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
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Student> getStudentsByClassSubjectId(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Student> q = b.createQuery(Student.class);
        Root<StudentEnrollment> root = q.from(StudentEnrollment.class);

        q.select(root.get("student"));
        q.where(b.equal(root.get("classSubject").get("id"), classSubjectId));
        q.distinct(true);

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Student findStudentById(Integer id) {
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
}