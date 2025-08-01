/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;
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
    public User getUserByUsername(String username) {
        try (Session s = factory.getObject().openSession()) {
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
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
    public Student studentRegister(Student student) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(student);
        session.refresh(student);
        return student;
    }

    @Override
    public Teacher teacherRegister(Teacher teacher) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(teacher);
        session.refresh(teacher);
        return teacher;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        return this.passwordEncoder.matches(password, user.getPassword());
    }


    @Override
    public User update(User user) {
        Session session = factory.getObject().getCurrentSession();
        session.merge(user);
        return user;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return findUserByEmail(email) != null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        return getUserByUsername(username) != null;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Session s = factory.getObject().openSession()) {
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("email"), email));
            return s.createQuery(query).uniqueResult();
        }catch (AppException ex){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
    }

    @Override
    public User findById(String id) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public String findIdByUserName(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<User> root = query.from(User.class);

        query.select(root.get("id").as(String.class))
                .where(builder.equal(root.get("username"), username));

        return s.createQuery(query).uniqueResult();
    }
}
