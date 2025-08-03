package com.scm.repositories.impl;

import com.scm.pojo.User;
import com.scm.pojo.UserTest;
import com.scm.repositories.UserTestRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserTestRepositoryImpl implements UserTestRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public UserTest findByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<UserTest> query = builder.createQuery(UserTest.class);
        Root<UserTest> root = query.from(UserTest.class);
        query.select(root).where(builder.equal(root.get("username"), username));
        return s.createQuery(query).uniqueResult();
    }

    @Override
    public void save(UserTest user) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(user);
    }

    public List<UserTest> findAll() {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTest> cq = cb.createQuery(UserTest.class);
        Root<UserTest> root = cq.from(UserTest.class);
        cq.select(root);

        return session.createQuery(cq).getResultList();
    }
}
