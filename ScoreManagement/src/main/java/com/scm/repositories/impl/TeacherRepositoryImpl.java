package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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
import java.util.Map;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    private static int PAGE_SIZE = 10;

    @Override
    public Teacher findById(String id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Teacher.class, id);
    }

    @Override
    public String findIdByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Teacher> root = cq.from(Teacher.class);
        cq.select(root.get("id")).where(cb.equal(root.get("username"), username));
        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public void updateRoleTeacher(String teacherId) {
        Session s = factory.getObject().getCurrentSession();
        Teacher teacher =  s.get(Teacher.class, teacherId);
        teacher.setRole("ROLE_TEACHER_SUPER");
        s.merge(teacher);
    }

    @Override
    public void downRoleTeacher(String teacherId) {
        Session s = factory.getObject().getCurrentSession();
        Teacher teacher =  s.get(Teacher.class, teacherId);
        teacher.setRole("ROLE_TEACHER");
        s.merge(teacher);
    }

    @Override
    public List<Teacher> getAllTeachersByRole(String role, String page) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.select(root).where(cb.equal(root.get("role"), role));

        Query q = s.createQuery(query);
        if (page != null) {
            int p = Integer.parseInt(page);
            int start = (p - 1) * PAGE_SIZE;
            q.setMaxResults(PAGE_SIZE);
            q.setFirstResult(start);
        }

        return q.getResultList();
    }


    @Override
    public void delete(Teacher teacher) {
        Session s = factory.getObject().getCurrentSession();
        s.remove(teacher);
    }

    @Override
    public Teacher getTeacherByClassDetailId(String classDetailId) {
        try {
            Session session = factory.getObject().getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);

            Root<ClassDetails> root = query.from(ClassDetails.class);
            Join<ClassDetails, Teacher> join = root.join("teacher");

            query.select(join).where(cb.equal(root.get("id"), classDetailId));

            return session.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
