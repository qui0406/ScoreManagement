/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.*;
import com.scm.exceptions.AppException;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.*;

import java.io.InputStreamReader;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api/secure/teacher")
@CrossOrigin
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
    private UserMapper  userMapper;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomResponse>> getMyClassrooms(Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            List<ClassroomResponse> classrooms = this.classroomService.getClassroomsByTeacherId(teacher.getId().toString());
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
    public ResponseEntity<?> addListScoreAllStudents(@RequestBody List<ListScoreStudentRequest> requests, Principal principal) {
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
    public ResponseEntity<List<ScoreStudentResponse>> getExportListScore(@PathVariable(value="classSubjectId") String classSubjectId
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
    public ResponseEntity<?> uploadScores(@RequestParam(value = "file") MultipartFile file, Principal principal) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        log.info("Principal: {}", principal);
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("SecurityContext: {}", context.getAuthentication());
        try {
            String username = principal.getName();
            log.info("Username: {}", username);
            User teacher = userDetailsService.getUserByUsername(username);
            log.info("Teacher: {}", teacher);
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            log.info("InputStreamReader: {}", reader);
            CsvToBean<CSVScoreRequest> csvToBean = new CsvToBeanBuilder<CSVScoreRequest>(reader)
                    .withType(CSVScoreRequest.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(true)
                    .build();
            log.info("CsvToBean created: {}", csvToBean);
            List<CSVScoreRequest> scores = csvToBean.parse();
            log.info("Parsed scores: {}", scores);
            if (csvToBean.getCapturedExceptions().size() > 0) {
                log.error("CSV parsing errors: {}", csvToBean.getCapturedExceptions());
                throw new Exception("Invalid CSV format: " + csvToBean.getCapturedExceptions().get(0).getMessage());
            }
            scores.forEach(score -> score.setTeacherId(teacher.getId()));
            log.info("Calling scoreService.importScores...");
            scoreService.importScores(scores);
            log.info("Scores imported successfully");
            return ResponseEntity.ok("Scores uploaded successfully.");
        } catch (Exception e) {
            log.error("Error processing file: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }



    @GetMapping("/class-subject/{classSubjectId}/scores")
    public ResponseEntity<List<ScoreResponse>> getGradesByClassSubject(@PathVariable(value="classSubjectId") Integer classSubjectId) {
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

    @PostMapping("/score/close-score/{classroomId}")
    public boolean closeScore(@PathVariable(value="classroomId") Integer classroomId, Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.updateCloseScore(teacher.getId(), classroomId);
            return true;
        }catch (AppException e) {
            e.printStackTrace();
            return false;
        }
    }

    // thông tin chi tiết lớp môn
    @GetMapping("/class-subject/{classSubjectId}/details")
    public ResponseEntity<ClassroomSubjectResponse> getClassroomSubjectDetails(@PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            ClassroomSubjectResponse details = this.classroomSubjectService.getClassroomSubjectDetails(classSubjectId);
            if (details == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // danh sách loại điểm của lớp môn
    @GetMapping("/class-subject/{classSubjectId}/score-types")
    public ResponseEntity<List<ScoreTypeResponse>> getGradeTypesByClassSubject(@PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            List<ScoreTypeResponse> gradeTypes = this.scoreTypeService.getScoreTypesByClassSubject(classSubjectId);
            return new ResponseEntity<>(gradeTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // thêm loại điểm mới cho lớp môn
    @PostMapping("/class-subject/{classSubjectId}/score-types")
    public ResponseEntity<String> addGradeTypeToClassSubject(
            @PathVariable Integer classSubjectId,
            @RequestBody ScoreTypeRequest scoreTypeRequest) {
        try {
            // giới hạn không quá 5 cột
            if (!this.scoreTypeService.canAddMoreGradeTypes(classSubjectId)) {
                return new ResponseEntity<>("Không thể thêm quá 5 loại điểm!", HttpStatus.BAD_REQUEST);
            }

            this.scoreTypeService.addGradeTypeToClassSubject(scoreTypeRequest, classSubjectId);
            return new ResponseEntity<>("Thêm loại điểm thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi thêm loại điểm: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}