/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.Student;
import com.scm.pojo.User;
import com.scm.repositories.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author QUI
 */
@Repository
@Transactional
public class UserRepositoryImp implements UserRepository{


    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Student getUserByUsername(String username) {
        try (Session s = factory.getObject().openSession()) {
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<Student> query = builder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root).where(builder.equal(root.get("username"), username));
            return s.createQuery(query).uniqueResult();
        }
    }



    @Override
    public List<User> getAllUsers() {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }


    @Override
    public Student register(Student student) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(student);
        session.refresh(student);
        return  student;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        return this.passwordEncoder.matches(password, user.getPassword());
    }





}
