/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.User;
import com.scm.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
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
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("username"), username)); // Add where clause

        Query query = session.createQuery(criteria);
       
        return (User) query.getSingleResult();
       
    }
    
    @Override
    public User register(User user) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(user);
        session.refresh(user);
        return  user;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        return this.passwordEncoder.matches(password, user.getPassword());
    }
    
}
