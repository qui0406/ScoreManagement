package com.scm.repositories.impl;

import com.scm.pojo.Conversation;
import com.scm.pojo.Message;
import com.scm.repositories.MessageRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class MessageRepositoryImpl implements MessageRepository {
    private static final int PAGE_SIZE = 20;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void create(Message message) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(message);
    }

    @Override
    public void update(Message message) {
        Session s = factory.getObject().getCurrentSession();
        s.merge(message);
    }

    @Override
    public void delete(Message message) {
        Session s = factory.getObject().getCurrentSession();
        s.remove(message);
    }

    @Override
    public Message findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Message.class, id);
    }

    @Override
    public List<Message> findAllMessagesByUserId(String userId, String page) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);

        query.select(root)
                .where(builder.equal(root.get("userId"), userId))
                .orderBy(builder.desc(root.get("createdAt")));

        Query q = session.createQuery(query);
        if (page != null && !page.isEmpty()) {
            int p = Integer.parseInt(page);
            int start = (p - 1) * PAGE_SIZE;
            q.setMaxResults(PAGE_SIZE);
            q.setFirstResult(start);
        }
        return q.getResultList();
    }
}
