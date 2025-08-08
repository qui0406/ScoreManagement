/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.responses.ClassResponse;

/**
 *
 * @author admin
 */
public interface ClassroomService {

    ClassResponse create(ClassroomRequest request);
    void delete(String classroomId);

    String getIdByClassName(String className);
}