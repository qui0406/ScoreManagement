/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.responses.*;
import com.scm.exceptions.AppException;
import com.scm.helpers.CSVHelper;
import com.scm.helpers.PDFHelper;
import com.scm.pojo.User;
import com.scm.services.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreTypeService scoreTypeService;

    @Autowired
    private ClassroomSubjectService classroomSubjectService;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @Autowired
    private TeacherService teacherService;


    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomResponse>> getMyClassrooms(Principal principal) {
        try {
            String teacherName = principal.getName();
            String teacherId = teacherService.findIdByUsername(teacherName);
            List<ClassroomResponse> classrooms = this.classroomService.getClassroomsByTeacherId(teacherId);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/{classSubjectId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsByClassSubject(@PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            List<StudentResponse> students = this.studentService.getStudentsByClassSubjectId(classSubjectId);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-scores")
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

    @PostMapping("/add-list-score")
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

    @PostMapping("/add-list-score-all-student")
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



    @GetMapping("/export-list-score/{classSubjectId}")
    public ResponseEntity<List<ScoreStudentResponse>> getExportListScore(
            @PathVariable(value="classSubjectId") String classSubjectId
            , Principal principal) {
        String teacherName = principal.getName();
        User teacher = userDetailsService.getUserByUsername(teacherName);
        try{
            return ResponseEntity.ok(this.scoreStudentService.getScoreByClassSubject(classSubjectId));
        }
        catch (AppException ex){
            return null;
        }
    }

    @GetMapping("/find-export-list-score/{classSubjectId}")
    public ResponseEntity<List<ScoreStudentResponse>> findExportListScore(
            @RequestParam Map <String, String> params,
            @PathVariable(value="classSubjectId") String classSubjectId
            ,Principal principal) {
        String teacherName = principal.getName();
        User teacher = userDetailsService.getUserByUsername(teacherName);
        try{
            return ResponseEntity.ok(this.scoreStudentService.findScoreByStudentId(params, classSubjectId));
        }
        catch (AppException ex){
            return null;
        }
    }


    @PostMapping(path = "/upload-scores", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> uploadScores(@RequestParam(value = "file") MultipartFile file,
                                          Principal principal) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try {
            List<ListScoreStudentRequest> listScoreStudentRequests = CSVHelper.parseScoreCSV(file);
            this.scoreService.addListScoreAllStudents(listScoreStudentRequests, "3");
        }
        catch (AppException ex){
            return ResponseEntity.badRequest().body("doc file that bai");
        }
        return ResponseEntity.ok("value: Successfully");
    }

    @PostMapping("/export-score")
    public ResponseEntity<String> exportScores(@RequestBody
                   List<ListScoreStudentRequest> listScoreStudentRequest,
                   Principal principal) throws Exception {
        try{
            PDFHelper.exportScoreListToPDF(listScoreStudentRequest);
            return ResponseEntity.ok("value: Successfully");
        }
        catch (AppException ex){
            return ResponseEntity.badRequest().body("value:" + ex.getErrorCode().getMessage());
        }
    }

    @GetMapping("/class-subject/{classSubjectId}/scores")
    public ResponseEntity<List<ScoreResponse>> getGradesByClassSubject(
            @PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            List<ScoreResponse> grades = this.scoreService.getScoresByClassSubjectId(classSubjectId);
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my-classes")
    public ResponseEntity<List<ClassroomSubjectResponse>> getMyClassroomSubjects(Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacherId = userDetailsService.getUserByUsername(teacherName);
            List<ClassroomSubjectResponse> classroomSubjects = this.classroomSubjectService
                    .getClassroomSubjectsByTeacherId(teacherId.getId().toString());

            return new ResponseEntity<>(classroomSubjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/score/close-score/{classSubjectId}")
    public boolean closeScore(@PathVariable(value="classSubjectId") Integer classSubjectId,
                              Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.updateCloseScore(teacher.getId(), classSubjectId);
            return true;
        }catch (AppException e) {
            e.printStackTrace();
            return false;
        }
    }

    // thông tin chi tiết lớp môn
    @GetMapping("/class-subject/{classSubjectId}/details")
    public ResponseEntity<ClassroomSubjectResponse> getClassroomSubjectDetails(
            @PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            ClassroomSubjectResponse details = this.classroomSubjectService
                    .getClassroomSubjectDetails(classSubjectId);
            if (details == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/score-types")
    public ResponseEntity<List<ScoreTypeResponse>> getScoreTypes() {
        try {
            List<ScoreTypeResponse> scoreTypes = this.scoreTypeService.getScoreTypes();
            return new ResponseEntity<>(scoreTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/score-types/{classSubjectId}")
    public ResponseEntity<List<ScoreTypeResponse>> getScoreTypesByClassSubject(
            @PathVariable(value="classSubjectId")
             String classSubjectId) {
        try {
            List<ScoreTypeResponse> scoreTypes = this.scoreTypeService
                    .getScoreTypesByClassSubject(classSubjectId);
            return new ResponseEntity<>(scoreTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/class-subject/score-type/{classSubjectId}/add")
    public ResponseEntity<?> addScoreType(
            @PathVariable(value = "classSubjectId") String classSubjectId,
            @RequestParam(value = "scoreTypeId") String scoreTypeId,
            Principal principal) {
        String teacherName = principal.getName();
        User teacherId = userDetailsService.getUserByUsername(teacherName);
        this.scoreTypeService.addScoreType(classSubjectId, scoreTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/class-subject/score-type/{classSubjectId}/delete")
    public ResponseEntity<?> deleteScoreType(
            @PathVariable(value = "classSubjectId") String classSubjectId,
            @RequestParam(value = "scoreTypeId") String scoreTypeId,
            Principal principal) {
        String teacherName = principal.getName();
        User teacherId = userDetailsService.getUserByUsername(teacherName);
        this.scoreTypeService.deleteScoreType(classSubjectId, scoreTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}