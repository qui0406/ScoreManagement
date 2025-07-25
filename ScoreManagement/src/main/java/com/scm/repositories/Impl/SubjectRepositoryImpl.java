package com.scm.repositories.Impl;

import com.scm.pojo.ClassSubject;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Subject;
import com.scm.repositories.SubjectRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Subject findSubjectById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Subject.class, id);
    }

    @Override
    public List<Subject> getAllSubjectsByStudentId(String studentId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);

        Root<StudentEnrollment> root = query.from(StudentEnrollment.class);
        Join<StudentEnrollment, ClassSubject> classSubjectJoin = root.join("classSubject");
        Join<ClassSubject, Subject> subjectJoin = classSubjectJoin.join("subject");

        query.select(subjectJoin).where(
                builder.equal(root.get("student").get("id"), studentId)
        );

        List<Subject> subjects = session.createQuery(query).getResultList();
        return subjects;
    }
}
