package com.scm.repositories.Impl;

import com.scm.pojo.Classroom;
import com.scm.pojo.Score;
import com.scm.pojo.ScoreType;
import com.scm.pojo.Teacher;
import com.scm.repositories.TeacherRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Teacher findTeacherById(Integer id) {
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
}
