package com.scm.repositories.Impl;

import com.scm.pojo.Faculty;
import com.scm.pojo.Semester;
import com.scm.repositories.SemesterRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SemesterRepositoryImpl implements SemesterRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Semester findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Semester.class, id);
    }

    @Override
    public void create(Semester semester) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(semester);
    }

    @Override
    public void delete(Semester semester) {
        Session s = factory.getObject().getCurrentSession();
        s.remove(semester);
    }
}
