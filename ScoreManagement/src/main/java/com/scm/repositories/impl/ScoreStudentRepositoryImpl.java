package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.EnrollDetails;
import com.scm.pojo.Score;
import com.scm.pojo.Student;
import com.scm.repositories.ScoreStudentRepository;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@Transactional
public class ScoreStudentRepositoryImpl implements ScoreStudentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Score> getScoresByStudentAndClass(String studentId, String classDetailId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Score> query = builder.createQuery(Score.class);
            Root<Score> root = query.from(Score.class);

            query.select(root).where(
                    builder.equal(root.get("student").get("id"), studentId),
                    builder.equal(root.get("classDetails").get("id"), classDetailId)
            );
            List<Score> result = session.createQuery(query).getResultList();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Score> getScoresByStudentAndClassWhenBlockScore(String studentId, String classDetailId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Score> query = builder.createQuery(Score.class);
            Root<Score> root = query.from(Score.class);

            query.select(root).where(
                    builder.equal(root.get("student").get("id"), studentId),
                    builder.equal(root.get("classDetails").get("id"), classDetailId),
                    builder.equal(root.get("active"), false)
            );
            List<Score> result = session.createQuery(query).getResultList();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    public List<Student> findScoreStudentByMSSVOrName(Map<String, String> params, String  classDetailId) {
        try {
            Session session = factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Student> q = b.createQuery(Student.class);

            Root<EnrollDetails> root = q.from(EnrollDetails.class);
            Join<EnrollDetails, Student> studentJoin = root.join("student");
            Join<EnrollDetails, ClassDetails> classJoin = root.join("classDetails");

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(classJoin.get("id"), classDetailId));

            if (params != null) {
                String mssv = params.get("mssv");
                if (mssv != null && !mssv.trim().isEmpty()) {
                    predicates.add(b.like(b.lower(studentJoin.get("mssv")), "%" + mssv.trim().toLowerCase() + "%"));
                }

                String fullName = params.get("fullName");
                if (fullName != null && !fullName.trim().isEmpty()) {
                    fullName = fullName.replaceAll("\\s+", "").toLowerCase().trim();
                    Expression<String> lastName = b.lower(studentJoin.get("lastName"));
                    Expression<String> firstName = b.lower(studentJoin.get("firstName"));
                    Expression<String> fullNameExpr = b.concat(lastName, firstName);
                    predicates.add(b.like(fullNameExpr, "%" + fullName + "%"));
                }
            }

            q.select(studentJoin).where(b.and(predicates.toArray(new Predicate[0])));
            List<Student> result = session.createQuery(q).getResultList();
            return result != null ? result : Collections.emptyList();

        } catch (Exception e) {
            throw new AppException(ErrorCode.SCORE_TYPE_INCORRECT);
        }
    }
}
