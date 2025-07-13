package com.scm.repositories.Impl;

import com.scm.pojo.ClassRoom;
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
    public List<ClassRoom> getListClassRoom() {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<ClassRoom> criteria = builder.createQuery(ClassRoom.class);
        Root<ClassRoom> root = criteria.from(ClassRoom.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    @Override
    public ClassRoom getClassRoomById(Integer classroomId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<ClassRoom> criteria = builder.createQuery(ClassRoom.class);
        Root<ClassRoom> root = criteria.from(ClassRoom.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), classroomId));
        return s.createQuery(criteria).getSingleResult();
    }
}
