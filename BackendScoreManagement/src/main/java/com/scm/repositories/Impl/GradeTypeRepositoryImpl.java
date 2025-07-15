/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories.Impl;

import com.scm.pojo.GradeType;
import com.scm.repositories.GradeTypeRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
public class GradeTypeRepositoryImpl implements GradeTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<GradeType> getGradeTypesByClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "SELECT DISTINCT g.gradeType FROM Grade g WHERE g.classSubject.id = :classSubjectId ORDER BY g.gradeType.id";
        Query query = s.createQuery(hql, GradeType.class);
        query.setParameter("classSubjectId", classSubjectId);
        return query.getResultList();
    }

    @Override
    public List<GradeType> getDefaultGradeTypes() {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "FROM GradeType gt WHERE gt.gradeTypeName IN ('Điểm giữa kỳ', 'Điểm cuối kỳ') ORDER BY gt.id";
        Query query = s.createQuery(hql, GradeType.class);
        return query.getResultList();
    }

//    @Override
//    public List<GradeType> getAllGradeTypes() {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = s.getCriteriaBuilder();
//        CriteriaQuery<GradeType> q = b.createQuery(GradeType.class);
//        Root<GradeType> root = q.from(GradeType.class);
//
//        q.select(root);
//        q.orderBy(b.asc(root.get("id")));
//
//        Query query = s.createQuery(q);
//        return query.getResultList();
//    }

    @Override
    public GradeType getGradeTypeById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(GradeType.class, id);
    }

    @Override
    public void addOrUpdateGradeType(GradeType gradeType) {
        Session s = this.factory.getObject().getCurrentSession();
        if (gradeType.getId() == null) {
            s.persist(gradeType);
        } else {
            s.merge(gradeType);
        }
    }

    @Override
    public void deleteGradeType(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        GradeType gradeType = this.getGradeTypeById(id);
        if (gradeType != null) {
            s.remove(gradeType);
        }
    }

    @Override
    public long countGradeTypesByClassSubject(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();

        // Đếm số loại điểm đã được sử dụng trong lớp môn cụ thể
        String hql = "SELECT COUNT(DISTINCT g.gradeType.id) FROM Grade g WHERE g.classSubject.id = :classSubjectId";
        Query query = s.createQuery(hql, Long.class);
        query.setParameter("classSubjectId", classSubjectId);

        return (Long) query.getSingleResult();
    }

    @Override
    public void initializeDefaultGradeTypes(Integer classSubjectId) {
        Session s = this.factory.getObject().getCurrentSession();

        // Kiểm tra xem đã có điểm giữa kỳ và cuối kỳ chưa
        String checkHql = "SELECT COUNT(DISTINCT g.gradeType) FROM Grade g WHERE g.classSubject.id = :classSubjectId AND g.gradeType.gradeTypeName IN ('Điểm giữa kỳ', 'Điểm cuối kỳ')";
        Query checkQuery = s.createQuery(checkHql, Long.class);
        checkQuery.setParameter("classSubjectId", classSubjectId);
        Long existingCount = (Long) checkQuery.getSingleResult();

        // Nếu chưa có đủ 2 loại điểm mặc định, tạo thêm
        if (existingCount < 2) {
            // Logic khởi tạo loại điểm mặc định sẽ được xử lý ở service layer
        }
    }
}
