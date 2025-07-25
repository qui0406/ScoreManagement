/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.ClassSubject;
import com.scm.pojo.Classroom;
import com.scm.pojo.Student;
import com.scm.repositories.ClassroomRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
@Slf4j
public class ClassroomRepositoryImpl implements ClassroomRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Classroom> getClassroomsByTeacherId(String teacherId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Classroom> q = b.createQuery(Classroom.class);

            Root<ClassSubject> root = q.from(ClassSubject.class);
            q.select(root.get("classId"));

            q.where(b.equal(root.get("teacher").get("id"), teacherId));

            return s.createQuery(q).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Classroom> getListClassRoom() {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Classroom> criteria = builder.createQuery(Classroom.class);
        Root<Classroom> root = criteria.from(Classroom.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    @Override
    public Classroom findClassRoomById(Integer classroomId) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Classroom.class, classroomId);
    }

    @Override
    public List<Student> getStudentsInClassroom(String classroomId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        query.select(root)
                .where(builder.equal(root.get("classroom").get("id"), classroomId));

        return session.createQuery(query).getResultList();
    }
}