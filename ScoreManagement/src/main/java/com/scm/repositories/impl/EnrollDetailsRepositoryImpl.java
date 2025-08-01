package com.scm.repositories.impl;

import com.scm.pojo.EnrollDetails;
import com.scm.repositories.EnrollDetailsRepository;
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
public class EnrollDetailsRepositoryImpl implements EnrollDetailsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public EnrollDetails findById(String enrollDetailId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(EnrollDetails.class, enrollDetailId);
    }

    @Override
    public void create(EnrollDetails enrollDetails) {
        Session s = this.factory.getObject().getCurrentSession();
        try{
            s.persist(enrollDetails);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EnrollDetails enrollDetails) {
        Session s = this.factory.getObject().getCurrentSession();
        s.remove(enrollDetails);
    }

    @Override
    public boolean checkExist(String enrollId, String studentId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<EnrollDetails> root = query.from(EnrollDetails.class);

        query.select(cb.count(root)).where(
                cb.equal(root.get("student").get("id"), studentId),
                cb.equal(root.get("classDetails").get("id"), enrollId)
        );

        return session.createQuery(query).getSingleResult() > 0;
    }
}
