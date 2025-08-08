package com.scm.controllers;

import com.scm.dto.responses.SemesterResponse;
import com.scm.pojo.Semester;
import com.scm.services.SemesterService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/secure")
@RestController
public class ApiSemesterController {
    @Autowired
    private UserService userService;

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/semesters")
    public ResponseEntity<?> getAllSemester(Principal principal) {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }
}
