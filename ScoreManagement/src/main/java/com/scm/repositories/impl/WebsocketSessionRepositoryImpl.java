package com.scm.repositories.impl;

import com.scm.pojo.WebSocketSession;
import com.scm.repositories.WebSocketSessionRepository;
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
public class WebsocketSessionRepositoryImpl implements WebSocketSessionRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void save(WebSocketSession webSocketSession) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(webSocketSession);
    }

    @Override
    public void deleteSocketSession(String socketSessionId) {
        Session session = factory.getObject().getCurrentSession();
        session.remove(findSocketSession(socketSessionId));
    }

    @Override
    public WebSocketSession findSocketSession(String socketSessionId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WebSocketSession> query = builder.createQuery(WebSocketSession.class);
        Root<WebSocketSession> root = query.from(WebSocketSession.class);
        query.select(root).where(builder.equal(root.get("webSocketSessionId"), socketSessionId));

        return  session.createQuery(query).uniqueResult();
    }

    @Override
    public List<WebSocketSession> findAllSocketSessionsById(String userId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WebSocketSession> query = builder.createQuery(WebSocketSession.class);
        Root<WebSocketSession> root = query.from(WebSocketSession.class);
        query.select(root).where(builder.equal(root.get("userId"), userId));

        return session.createQuery(query).getResultList();
    }
}
