package com.scm.repositories.Impl;

import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Subject findSubjectById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Subject.class, id);
    }


}
