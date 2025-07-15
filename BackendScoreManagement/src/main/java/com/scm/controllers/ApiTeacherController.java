/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.GradeRequest;
import com.scm.dto.requests.GradeTypeRequest;
import com.scm.dto.responses.ClassroomResponse;
import com.scm.dto.responses.GradeResponse;
import com.scm.dto.responses.GradeTypeResponse;
import com.scm.dto.responses.StudentResponse;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.services.ClassroomService;
import com.scm.services.GradeService;
import com.scm.services.StudentService;
import com.scm.services.ClassroomSubjectService;
import com.scm.services.GradeTypeService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class ApiTeacherController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeTypeService gradeTypeService;

    @Autowired
    private ClassroomSubjectService classroomSubjectService;

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomResponse>> getMyClassrooms(Principal principal) {
        try {
            String teacherId = principal.getName();
            List<ClassroomResponse> classrooms = this.classroomService.getClassroomsByTeacherId(teacherId);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/{classSubjectId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsByClassSubject(@PathVariable Integer classSubjectId) {
        try {
            List<StudentResponse> students = this.studentService.getStudentsByClassSubjectId(classSubjectId);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/grades")
    public ResponseEntity<String> addOrUpdateGrade(@RequestBody GradeRequest gradeRequest, Principal principal) {
        try {
            String teacherId = principal.getName();
            this.gradeService.addOrUpdateGrade(gradeRequest, teacherId);
            return new ResponseEntity<>("Nhập điểm thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi nhập điểm: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/{classSubjectId}/grades")
    public ResponseEntity<List<GradeResponse>> getGradesByClassSubject(@PathVariable Integer classSubjectId) {
        try {
            List<GradeResponse> grades = this.gradeService.getGradesByClassSubjectId(classSubjectId);
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // danh sách lớp môn giảng viên đang dạy
    @GetMapping("/my-classes")
    public ResponseEntity<List<ClassroomSubjectResponse>> getMyClassroomSubjects(Principal principal) {
        try {
            String teacherId = principal.getName();
            List<ClassroomSubjectResponse> classroomSubjects = this.classroomSubjectService.getClassroomSubjectsByTeacherId(teacherId);
            return new ResponseEntity<>(classroomSubjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // thông tin chi tiết lớp môn
    @GetMapping("/class-subject/{classSubjectId}/details")
    public ResponseEntity<ClassroomSubjectResponse> getClassroomSubjectDetails(@PathVariable Integer classSubjectId) {
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
    @GetMapping("/class-subject/{classSubjectId}/grade-types")
    public ResponseEntity<List<GradeTypeResponse>> getGradeTypesByClassSubject(@PathVariable Integer classSubjectId) {
        try {
            List<GradeTypeResponse> gradeTypes = this.gradeTypeService.getGradeTypesByClassSubject(classSubjectId);
            return new ResponseEntity<>(gradeTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // thêm loại điểm mới cho lớp môn
    @PostMapping("/class-subject/{classSubjectId}/grade-types")
    public ResponseEntity<String> addGradeTypeToClassSubject(
            @PathVariable Integer classSubjectId,
            @RequestBody GradeTypeRequest gradeTypeRequest) {
        try {
            // giới hạn không quá 5 cột
            if (!this.gradeTypeService.canAddMoreGradeTypes(classSubjectId)) {
                return new ResponseEntity<>("Không thể thêm quá 5 loại điểm!", HttpStatus.BAD_REQUEST);
            }

            this.gradeTypeService.addGradeTypeToClassSubject(gradeTypeRequest, classSubjectId);
            return new ResponseEntity<>("Thêm loại điểm thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi thêm loại điểm: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
