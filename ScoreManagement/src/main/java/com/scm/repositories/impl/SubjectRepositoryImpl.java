package com.scm.repositories.impl;

import com.scm.pojo.ClassDetails;
import com.scm.pojo.Faculty;
import com.scm.pojo.EnrollDetails;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Slf4j
public class SubjectRepositoryImpl implements SubjectRepository {
    private static final int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Subject findById(String id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Subject.class, id);
    }

    @Override
    public List<Subject> getAllSubjectsByStudentId(String studentId, Map<String, String> params) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);

        Root<EnrollDetails> root = query.from(EnrollDetails.class);
        Join<EnrollDetails, ClassDetails> classSubjectJoin = root.join("classDetails");
        Join<ClassDetails, Subject> subjectJoin = classSubjectJoin.join("subject");

        query.select(subjectJoin).where(
                builder.equal(root.get("student").get("id"), studentId)
        );

        Query q = session.createQuery(query);
        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            q.setMaxResults(PAGE_SIZE);
            q.setFirstResult(start);
        }

        return q.getResultList();
    }

    @Override
    public List<Subject> getAllSubjectsInFacultySemester(String facultyId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);

        Root<Subject> root = query.from(Subject.class);
        Join<Subject, Faculty> facultyJoin = root.join("faculty");

        Subquery<ClassDetails> subquery = query.subquery(ClassDetails.class);
        Root<ClassDetails> subject = subquery.from(ClassDetails.class);

        Predicate subjectMatch = builder.equal(subject.get("subject"), root);
        Predicate semesterMatch = builder.equal(subject.get("semester").get("id"), semesterId);
        subquery.select(subject)
                .where(builder.and(subjectMatch, semesterMatch));

        Predicate facultyMatch = builder.equal(facultyJoin.get("id"), facultyId);
        Predicate existsSubquery = builder.exists(subquery);

        query.select(root).where(builder.and(facultyMatch, existsSubquery));
        return session.createQuery(query).getResultList();
    }

    @Override
    public Subject create(Subject subject) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(subject);
        return subject;
    }

    @Override
    public void delete(Subject subject) {
        Session s = factory.getObject().getCurrentSession();
        s.remove(subject);
    }

    @Override
    public List<Subject> getSubjectsCurrentSemester(String studentId, String semesterId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);

        Root<EnrollDetails> root = query.from(EnrollDetails.class);
        Join<EnrollDetails, ClassDetails> classSubjectJoin = root.join("classDetails");
        Join<ClassDetails, Subject> subjectJoin = classSubjectJoin.join("subject");

        query.select(subjectJoin).where(
                builder.equal(root.get("student").get("id"), studentId),
                builder.equal(root.get("semester").get("id"), semesterId)
        );
        return session.createQuery(query).getResultList();
    }

    @Override
    public List<Subject> getAllSubjects(String page) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        Root<Subject> root = query.from(Subject.class);

        query.select(root);
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
