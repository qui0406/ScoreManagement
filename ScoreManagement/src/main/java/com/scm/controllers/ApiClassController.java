package com.scm.controllers;

import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.requests.EnrollClassRequest;
import com.scm.dto.responses.ClassDetailsResponse;
import com.scm.dto.responses.StudentResponse;
import com.scm.pojo.User;
import com.scm.services.ClassroomDetailsService;
import com.scm.services.EnrollDetailsService;
import com.scm.services.StudentService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/secure/user")
public class ApiClassController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private EnrollDetailsService enrollDetailsService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomDetailsService classroomDetailsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    // Đăng ký môn học, lớp học
    @PostMapping("/register-class")
    public ResponseEntity<?> registerClass(@RequestBody EnrollClassRequest request, Principal principal) {
        String name = principal.getName();
        User student = userDetailsService.getUserByUsername(name);
        this.enrollDetailsService.create(request, student.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete-class/{enrollId}")
    public ResponseEntity<?> deleteClass(@PathVariable("enrollId") String enrollId) {
        this.enrollDetailsService.delete(enrollId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list-student-enrollment/{classDetailId}")
    public ResponseEntity<List<StudentResponse>> getEnrollment(@PathVariable("classDetailId") String classDetailId) {
        return ResponseEntity.ok(this.studentService.getAllStudentsByClass(classDetailId));
    }

    @GetMapping("/get-my-classes/{classDetailId}")
    public ResponseEntity<ClassDetailsResponse> getClassDetails(@PathVariable("classDetailId")
                                            String classDetailId, Principal principal) {
        String name = principal.getName();
        User student = userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.classroomDetailsService.getClassDetailsById(classDetailId, student.getId().toString()));
    }

    @GetMapping("/get-all-my-class")
    public ResponseEntity<List<ClassDetailsResponse>> getAllClassDetails(Principal principal) {
        String name =  principal.getName();
        User student = userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.classroomDetailsService.getClassroomByStudentId(student.getId().toString()));
    }
    @GetMapping("/get-all-class-details")
    public ResponseEntity<List<ClassDetailsResponse>> getAllClassDetails() {
        return ResponseEntity.ok(this.classroomDetailsService.getAllClassDetails());
    }
}
