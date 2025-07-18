/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.ScoreRequest;
import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.*;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.*;

import java.security.Principal;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/scores")
    public ResponseEntity<String> addOrUpdateGrade(@RequestBody ScoreRequest scoreRequest, Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.addOrUpdateScore(scoreRequest, teacher.getId().toString());
            return new ResponseEntity<>("Nhập điểm thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi nhập điểm: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/{classSubjectId}/grades")
    public ResponseEntity<List<ScoreResponse>> getGradesByClassSubject(@PathVariable(value="classSubjectId") Integer classSubjectId) {
        try {
            List<ScoreResponse> grades = this.scoreService.getScoresByClassSubjectId(classSubjectId);
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // danh sách lớp môn giảng viên đang dạy
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