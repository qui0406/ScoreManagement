package com.scm.repositories.impl;

import com.scm.pojo.ClassDetails;
import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;
import com.scm.repositories.ForumRepository;
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

@Repository
@Transactional
public class ForumRepositoryImpl implements ForumRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void create(Forum forum) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(forum);
    }

    @Override
    public void delete(String id) {
        Session s = factory.getObject().getCurrentSession();
        Forum forum = findById(id);
        s.remove(forum);
    }

    @Override
    public void update(Forum forum) {
        Session s = factory.getObject().getCurrentSession();
        s.merge(forum);
    }

    @Override
    public Forum findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Forum.class, id);
    }

    @Override
    public List<Forum> getAllForumsByClassDetailId(String classDetailId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Forum> q = b.createQuery(Forum.class);
        Root<Forum> root = q.from(Forum.class);
        q.select(root);
        q.where(b.equal(root.get("classDetails").get("id"), classDetailId));
        return s.createQuery(q).getResultList();
    }
}
