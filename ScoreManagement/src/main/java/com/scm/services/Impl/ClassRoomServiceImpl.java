package com.scm.services.Impl;

import com.scm.pojo.Classroom;
import com.scm.repositories.ClassRoomRepository;
import com.scm.services.ClassRoomService;
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
public class ClassRoomServiceImpl implements ClassRoomService {

}