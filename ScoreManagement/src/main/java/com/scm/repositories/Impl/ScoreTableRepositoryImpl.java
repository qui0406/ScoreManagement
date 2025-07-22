package com.scm.repositories.Impl;

import com.scm.pojo.ClassroomSubject;
import com.scm.pojo.Score;
import com.scm.repositories.ScoreTableRepository;
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
public class ScoreTableRepositoryImpl implements ScoreTableRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Score> getScoreSubjectByStudentId(String id,  String classroomSubjectId, String teacherId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> root = query.from(Score.class);

        query.select(root).where(
            builder.equal(root.get("student").get("id"), id),
            builder.equal(root.get("classSubject").get("id"), classroomSubjectId),
            builder.equal(root.get("teacher").get("id"), teacherId)
        ).orderBy(builder.asc(root.get("scoreType").get("id")));

        List<Score> scores = session.createQuery(query).getResultList();
        return scores;
    }
}
