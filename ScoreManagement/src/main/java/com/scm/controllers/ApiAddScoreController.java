package com.scm.controllers;

import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.exceptions.AppException;
import com.scm.pojo.User;
import com.scm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/secure/teacher")
public class ApiAddScoreController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/add-score")
    public ResponseEntity<String> addOrUpdateScore(@RequestBody ScoreRequest scoreRequest, Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.addOrUpdateScore(scoreRequest, teacher.getId().toString());
            return ResponseEntity.ok("value: Successfully");
        } catch (AppException e) {
            return ResponseEntity.badRequest().body("value:" + e.getErrorCode().getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("value: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-list-scores")
    public ResponseEntity<?> addListScore(@RequestBody ListScoreStudentRequest listScoreStudentRequest, Principal principal) {
        try{
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.addListScore(listScoreStudentRequest, teacher.getId().toString());
            return ResponseEntity.ok("value: Successfully");
        }
        catch (AppException e) {
            return ResponseEntity.badRequest().body("value:" + e.getErrorCode().getMessage());
        }
    }

    @PostMapping("/add-list-scores-all-student")
    public ResponseEntity<?> addListScoreAllStudents(@RequestBody
                                 List<ListScoreStudentRequest> requests, Principal principal) {
        try{
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.addListScoreAllStudents(requests, teacher.getId().toString());
            return ResponseEntity.ok("value: Successfully");
        }
        catch (AppException e) {
            return ResponseEntity.badRequest().body("value:" + e.getErrorCode().getMessage());
        }
    }
}
