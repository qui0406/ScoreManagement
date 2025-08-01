/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ReadFileCSVRequest;
import com.scm.dto.responses.*;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.helpers.CSVHelper;
import com.scm.helpers.PDFHelper;
import com.scm.pojo.ScoreType;
import com.scm.pojo.User;
import com.scm.services.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api/secure/teacher")
@Slf4j
public class ApiTeacherController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ClassroomDetailsService classroomDetailsService;

    @Autowired
    private ScoreStudentService scoreStudentService;


    @GetMapping("/my-classrooms")
    public ResponseEntity<List<ClassResponse>> getMyClassrooms(Principal principal) {
        try {
            String teacherName = principal.getName();
            User user = userDetailsService.getUserByUsername(teacherName);
            List<ClassResponse> classrooms = this.classroomDetailsService.getClassroomsByTeacherId(user.getId().toString());
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/export-list-score/{classDetailId}")
    public ResponseEntity<List<ScoreStudentResponse>> getExportListScore(
            @PathVariable(value="classDetailId") String classDetailId
            ,Principal principal) {
        String teacherName = principal.getName();
        User teacher = userDetailsService.getUserByUsername(teacherName);
        try{
            return ResponseEntity.ok(this.scoreStudentService.getScoreByClassDetails(classDetailId, teacher.getId().toString()));
        }
        catch (AppException ex){
            return null;
        }
    }

    @GetMapping("/find-export-list-score/{classDetailId}")
    public ResponseEntity<List<ScoreStudentResponse>> findExportListScore(
            @RequestParam Map<String, String> params,
            @PathVariable(value="classDetailId") String classDetailId
            ,Principal principal) {
        String teacherName = principal.getName();
        User teacher = userDetailsService.getUserByUsername(teacherName);
        try{
            return ResponseEntity.ok(this.scoreStudentService.findScoreByStudentId(params, classDetailId));
        }
        catch (AppException ex){
            throw new AppException(ErrorCode.INVALID_DATA);
        }
    }

}