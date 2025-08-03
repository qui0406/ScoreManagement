package com.scm.repositories.impl;

import com.scm.pojo.MessageTest;
import com.scm.repositories.MessageTestRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MessageTestRepositoryImpl implements MessageTestRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<MessageTest> findByOrderByTimestampAsc() {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MessageTest> cq = cb.createQuery(MessageTest.class);
        Root<MessageTest> root = cq.from(MessageTest.class);

        cq.select(root).orderBy(cb.asc(root.get("timestamp")));
        return session.createQuery(cq).getResultList();
    }

    @Override
    public void save(MessageTest messageTest) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(messageTest);
    }

    @Override
    public List<MessageTest> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Long senderId1, Long receiverId1,
            Long receiverId2, Long senderId2) {

        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MessageTest> cq = cb.createQuery(MessageTest.class);
        Root<MessageTest> root = cq.from(MessageTest.class);

        // (senderId = senderId1 AND receiverId = receiverId1)
        Predicate p1 = cb.and(
                cb.equal(root.get("senderId"), senderId1),
                cb.equal(root.get("receiverId"), receiverId1)
        );

        // (senderId = senderId2 AND receiverId = receiverId2)
        Predicate p2 = cb.and(
                cb.equal(root.get("senderId"), senderId2),
                cb.equal(root.get("receiverId"), receiverId2)
        );

        cq.select(root)
                .where(cb.or(p1, p2))
                .orderBy(cb.asc(root.get("timestamp"))); // sắp xếp theo thời gian tăng dần

        return session.createQuery(cq).getResultList();
    }

}
