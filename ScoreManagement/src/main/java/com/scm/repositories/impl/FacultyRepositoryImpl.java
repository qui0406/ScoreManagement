package com.scm.repositories.impl;

import com.scm.pojo.Faculty;
import com.scm.repositories.FacultyRepository;
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
public class FacultyRepositoryImpl implements FacultyRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Faculty findById(String facultyId) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Faculty.class, facultyId);
    }

    @Override
    public List<Faculty> findAllFaculties() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Faculty> query = builder.createQuery(Faculty.class);

        Root<Faculty> root = query.from(Faculty.class);
        query.select(root);
        return session.createQuery(query).getResultList();
    }
}
