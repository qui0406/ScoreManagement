package com.scm.repositories.Impl;

import com.scm.pojo.Classroom;
import com.scm.repositories.ClassRoomRepository;
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
public class ClassRoomRepositoryImpl implements ClassRoomRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Classroom> getListClassRoom() {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Classroom> criteria = builder.createQuery(Classroom.class);
        Root<Classroom> root = criteria.from(Classroom.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    @Override
    public Classroom getClassRoomById(Integer classroomId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Classroom> criteria = builder.createQuery(Classroom.class);
        Root<Classroom> root = criteria.from(Classroom.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), classroomId));
        return s.createQuery(criteria).getSingleResult();
    }
}
