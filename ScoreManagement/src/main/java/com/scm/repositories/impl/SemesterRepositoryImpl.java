package com.scm.repositories.impl;

import com.scm.pojo.Faculty;
import com.scm.pojo.Semester;
import com.scm.repositories.SemesterRepository;
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

    @Override
    public List<Semester> getAllSemesters() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Semester> query = builder.createQuery(Semester.class);

        Root<Semester> root = query.from(Semester.class);
        query.select(root);
        return session.createQuery(query).getResultList();
    }
}
