package com.scm.repositories;

import com.scm.pojo.UserTest;

import java.util.List;

public interface UserTestRepository {
    UserTest findByUsername(String username);
    void save(UserTest user);
    List<UserTest> findAll();
}
