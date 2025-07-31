//package com.scm.repositories.Impl;
//
//import com.scm.pojo.EnrollDetails;
//import com.scm.repositories.StudentEnrollmentRepository;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import org.hibernate.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public class StudentEnrollmentRepositoryImpl implements StudentEnrollmentRepository {
//    @Autowired
//    private LocalSessionFactoryBean factory;
//    @Override
//    public List<EnrollDetails> findAllByClassSubjectId(String studentId) {
//        Session session = factory.getObject().getCurrentSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<EnrollDetails> query = builder.createQuery(EnrollDetails.class);
//        Root<EnrollDetails> root = query.from(EnrollDetails.class);
//
//        query.select(root).where(
//                builder.equal(root.get("student").get("id"), studentId)
//        );
//
//        List<EnrollDetails> results = session.createQuery(query).getResultList();
//        return results;
//    }
//
//    @Override
//    public void create(EnrollDetails enrollDetails) {
//        Session session = factory.getObject().getCurrentSession();
//        session.persist(enrollDetails);
//    }
//
//    @Override
//    public void delete(EnrollDetails enrollDetails) {
//        Session session = factory.getObject().getCurrentSession();
//        session.remove(enrollDetails);
//    }
//}
