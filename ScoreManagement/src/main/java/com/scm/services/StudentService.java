/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.scm.services;

import com.scm.dto.requests.Recipient;
import com.scm.dto.responses.StudentResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface StudentService {
    List<StudentResponse> getAllStudentsByClass(String classDetailId);

    List<Recipient> getAllRecipientStudentsByClass(String classDetailId);

    String findIdByUsername(String username);
    String getIdByMssv(String mssv);

    List<String> getAllMssvByClass(String classDetailId);
}