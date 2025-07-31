package com.scm.repositories.Impl;

import com.scm.pojo.ClassDetailsScoreType;
import com.scm.repositories.ClassDetailsScoreTypeRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ClassDetailsScoreTypeRepositoryImpl implements ClassDetailsScoreTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void save(ClassDetailsScoreType classSubjectScore) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(classSubjectScore);
    }

    @Override
    public void delete(ClassDetailsScoreType classSubjectScore) {
        Session session = factory.getObject().getCurrentSession();
        session.remove(classSubjectScore);
    }
}
