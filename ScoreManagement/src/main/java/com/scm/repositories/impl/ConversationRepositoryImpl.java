package com.scm.repositories.impl;

import com.scm.pojo.Conversation;
import com.scm.repositories.ConversationRepository;
import jakarta.persistence.TypedQuery;
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
public class ConversationRepositoryImpl implements ConversationRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void create(Conversation conversation) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(conversation);
    }

    @Override
    public void delete(Conversation conversation) {
        Session s = factory.getObject().getCurrentSession();
        s.remove(conversation);
    }

    @Override
    public Conversation findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Conversation.class, id);
    }

    @Override
    public List<Conversation> getAllConversationsByUserId(String userId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Conversation> cq = b.createQuery(Conversation.class);
        Root<Conversation> root = cq.from(Conversation.class);
        cq.select(root);
        cq.where(b.equal(root.get("userId"), userId)).orderBy(b.desc(root.get("createdAt")));;
        return s.createQuery(cq).getResultList();
    }
}
