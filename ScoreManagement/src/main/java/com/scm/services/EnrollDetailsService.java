package com.scm.services;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.pojo.EnrollDetails;

import java.security.Principal;

public interface EnrollDetailsService {
    void create(EnrollClassRequest request,  String studentId);
    void delete(String enrollId);
}
