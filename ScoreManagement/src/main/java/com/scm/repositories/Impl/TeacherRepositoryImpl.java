package com.scm.repositories.Impl;

import com.scm.pojo.Classroom;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
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
public class TeacherRepositoryImpl implements TeacherRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Teacher findTeacherById(Integer id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Teacher.class, id);
    }
}
