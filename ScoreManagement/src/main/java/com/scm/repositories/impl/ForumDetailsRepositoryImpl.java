package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;
import com.scm.repositories.ForumDetailsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ForumDetailsRepositoryImpl implements ForumDetailsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void create(ForumDetails forumDetails) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(forumDetails);
    }

    @Override
    public void delete(String id) {
        Session s = factory.getObject().getCurrentSession();
        ForumDetails forumDetails = findById(id);
        s.remove(forumDetails);
    }

    @Override
    public void update(ForumDetails forumDetails) {
        Session s = factory.getObject().getCurrentSession();
        s.merge(forumDetails);
    }

    @Override
    public ForumDetails findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(ForumDetails.class, id);
    }

    @Override
    public List<ForumDetails> findAllByForumId(Map<String,String> params) {
        if (!params.containsKey("forumId")) {
            throw new AppException(ErrorCode.FORUM_NOT_FOUND);
        }
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ForumDetails> q = b.createQuery(ForumDetails.class);
        Root<ForumDetails> root = q.from(ForumDetails.class);
        q.select(root);
        q.where(b.equal(root.get("forumId").get("id"), params.get("forumId")));
        return s.createQuery(q).getResultList();

    }

    @Override
    public List<ForumDetails> getAllByForumId(String forumId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ForumDetails> q = b.createQuery(ForumDetails.class);
        Root<ForumDetails> root = q.from(ForumDetails.class);
        q.select(root);
        q.where(b.equal(root.get("forum").get("id"), forumId));
        return s.createQuery(q).getResultList();
    }
}
