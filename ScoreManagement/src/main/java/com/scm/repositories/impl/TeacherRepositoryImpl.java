package com.scm.repositories.impl;

import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
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
        teacher.setRole("TEACHER_SUPER");
        s.merge(teacher);
    }

    @Override
    public List<Teacher> getAllTeachersByRole(Map<String, String> params) {
        if(params.containsKey("role")) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }

        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.select(root).where(cb.equal(root.get("role"), params.get("role")));

        Query q = s.createQuery(query);
        if (params.get("teacherId") != null) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            q.setMaxResults(PAGE_SIZE);
            q.setFirstResult(start);
        }

        return q.getResultList();
    }


}
