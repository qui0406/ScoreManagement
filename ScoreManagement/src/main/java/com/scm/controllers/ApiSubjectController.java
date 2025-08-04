package com.scm.controllers;

import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.User;
import com.scm.services.ClassroomDetailsService;
import com.scm.services.ScoreStudentService;
import com.scm.services.SubjectService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/secure/user")
public class ApiSubjectController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ClassroomDetailsService classroomDetailsService;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/get-all-classes-me-register")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(Principal principal,
                                                                @RequestParam Map<String, String> params) {
        String username = principal.getName();
        User student = userDetailsService.getUserByUsername(username);
        return ResponseEntity.ok(this.subjectService.getAllSubjectsByStudent(student.getId().toString(), params));
    }
}
