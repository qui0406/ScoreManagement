/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.repositories;

import com.scm.pojo.User;



/**
 *
 * @author QUI
 */
public interface UserRepository {
    User getUserByUsername(String username);
    User register(User user);
    boolean authenticate(String username, String password);

}
