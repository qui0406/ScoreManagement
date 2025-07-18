package com.scm.repositories.Impl;

import com.scm.pojo.Faculty;
import com.scm.pojo.Teacher;
import com.scm.repositories.FacultyRepository;
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
public class FacultyRepositoryImpl implements FacultyRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Faculty findFacultyById(Integer facultytId) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Faculty.class, facultytId);
    }
}
