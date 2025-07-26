package com.scm.repositories.Impl;

import com.scm.pojo.ClassSubjectScore;
import com.scm.repositories.ClassSubjectScoreRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ClassSubjectScoreRepositoryImpl implements ClassSubjectScoreRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void save(ClassSubjectScore classSubjectScore) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(classSubjectScore);
    }

    @Override
    public void delete(ClassSubjectScore classSubjectScore) {
        Session session = factory.getObject().getCurrentSession();
        session.remove(classSubjectScore);
    }
}
