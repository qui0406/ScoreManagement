package com.scm.repositories.Impl;

import com.scm.dto.responses.ScoreResponse;
import com.scm.pojo.Score;
import com.scm.pojo.Student;
import com.scm.repositories.ScoreStudentRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ScoreStudentRepositoryImpl implements ScoreStudentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Score getScoreByStudent(String studentId, String classSubjectId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> root = query.from(Score.class);

        query.select(root).where(
                builder.equal(root.get("student").get("id"), studentId),
                builder.equal(root.get("classSubject").get("id"), classSubjectId)
        );
        return session.createQuery(query).setMaxResults(1).getSingleResult();
    }


    @Override
    public List<Student> getAllStudentsInClassSubject(String classSubjectId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);

        Root<Score> root = cq.from(Score.class);
        cq.select(root.get("student")).distinct(true)
                .where(cb.equal(root.get("classSubject").get("id"), classSubjectId));

        return session.createQuery(cq).getResultList();
    }


    @Override
    public List<Score> getAllScoreByStudentAndClassSubject(String studentId, String classSubjectId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> root = query.from(Score.class);

        query.select(root).where(
                builder.equal(root.get("student").get("id"), studentId),
                builder.equal(root.get("classSubject").get("id"), classSubjectId)
        );
        return session.createQuery(query).getResultList();
    }

    @Override
    public List<Student> findScoreStudentByMSSVOrName(Map<String, String> params) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String mssv = params.getOrDefault("mssv", "").trim();
            if (!mssv.isEmpty()) {
                predicates.add(cb.like(root.get("mssv"), "%" + mssv + "%")); // dùng cb.like
            }

            String fullName = params.getOrDefault("fullName", "").trim();
            if (!fullName.isEmpty()) {
                Expression<String> fullNameExpr = cb.concat(
                        cb.concat(cb.lower(root.get("lastName")), " "),
                        cb.lower(root.get("firstName"))
                );
                predicates.add(cb.like(fullNameExpr, "%" + fullName.toLowerCase() + "%")); // dùng cb.like và toLowerCase
            }
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return session.createQuery(cq).getResultList();
    }

}
