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
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ClassroomDetailsService classroomDetailsService;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ScoreTypeService scoreTypeService;


    @PreAuthorize("hasRole('TEACHER')")
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


    @PostMapping(path = "/upload-scores/{classDetailId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> uploadScores(@RequestParam(value = "file") MultipartFile file,
                                          @PathVariable(value="classDetailId") String  classDetailId,
                                          Principal principal) {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_IS_EMPTY);        }
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            List<ReadFileCSVRequest> r = CSVHelper.parseScoreCSV(file);
            List<String> listMssv = new ArrayList<>();

            if (r == null) {
                throw new AppException(ErrorCode.FILE_IS_EMPTY);
            }
            for(ReadFileCSVRequest x : r){
                listMssv.add(x.getMssv());
            }

            if(!studentService.getAllMssvByClass(classDetailId).equals(listMssv)){
                throw new AppException(ErrorCode.LIST_STUDENT_NOT_SUITABLE);
            }

            List<ListScoreStudentRequest> request = new ArrayList<>();

            for (ReadFileCSVRequest readFileCSVRequest : r) {
                ListScoreStudentRequest tmp = new ListScoreStudentRequest();
                tmp.setStudentId(studentService.getIdByMssv(readFileCSVRequest.getMssv()));
                tmp.setClassDetailId(classDetailId);
                tmp.setScores(readFileCSVRequest.getScores());
                request.add(tmp);
            }
            this.scoreService.addListScoreAllStudents(request, teacher.getId().toString());
        }
        catch (AppException ex){
            throw new AppException(ErrorCode.READ_FILE_ERROR);
        }
        return ResponseEntity.ok("value: Successfully");
    }

    @PostMapping("/export-score/{classDetailId}")
    public ResponseEntity<String> exportScores(@PathVariable(value="classDetailId") String classDetailId,
            Principal principal) throws Exception {
        try{
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            List<WriteScoreStudentPDFResponse> request = this.scoreStudentService.listScorePDF(classDetailId, teacher.getId().toString());

            List<ScoreTypeResponse> listScoreType = this.scoreTypeService.getScoreTypesByClassDetails(classDetailId);
            List<String> listScoreTypeName = listScoreType.stream()
                    .map(ScoreTypeResponse::getScoreTypeName)
                    .collect(Collectors.toList());

            PDFHelper.exportScoreListToPDF(request, listScoreTypeName);
            return ResponseEntity.ok("value: Successfully");
        }
        catch (AppException ex){
            return ResponseEntity.badRequest().body("value:" + ex.getErrorCode().getMessage());
        }
    }



    @PostMapping("/score/block-score/{classDetailId}")
    public ResponseEntity<?> blockScore(@PathVariable(value="classDetailId") String classDetailId,
                              Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.blockScore(teacher.getId().toString(), classDetailId);
            return ResponseEntity.ok("value: Successfully");
        }catch (AppException e) {
            throw new AppException(ErrorCode.BLOCK_SCORE_ERROR);
        }
    }

    @GetMapping("/score/get-status/{classDetailId}")
    public ResponseEntity<?> getStatus(@PathVariable(value="classDetailId") String classDetailId) {
        return ResponseEntity.ok(this.scoreService.getStatusBlock(classDetailId));
    }

//
//    // thông tin chi tiết lớp môn
//    @GetMapping("/class-subject/{classSubjectId}/details")
//    public ResponseEntity<ClassDetailsResponse> getClassroomSubjectDetails(
//            @PathVariable(value="classSubjectId") Integer classSubjectId) {
//        try {
//            ClassDetailsResponse details = this.classroomSubjectService
//                    .getClassroomSubjectDetails(classSubjectId);
//            if (details == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(details, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



}