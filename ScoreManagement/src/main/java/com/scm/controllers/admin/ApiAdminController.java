package com.scm.controllers.admin;

import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.requests.SubjectRequest;
import com.scm.exceptions.AppException;
import com.scm.pojo.User;
import com.scm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/secure/staff")
public class ApiAdminController {
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ClassroomDetailsService classroomDetailsService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create/classroom")
    public ResponseEntity<?> createClassroom(@RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(classroomService.create(request));
    }

    @DeleteMapping("/delete/{classroomId}")
    public ResponseEntity<?> deleteClassroom(@PathVariable(value= "classroomId") String classroomId) {
        this.classroomService.delete(classroomId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/class-details/create")
    public ResponseEntity<?> registerSubject(@RequestBody CreateClassDetailsRequest request, Principal principal) {
        try{
            this.classroomDetailsService.create(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(AppException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/class-details/delete/{classDetailId}")
    public ResponseEntity<?> registerSubject(@PathVariable(value = "classDetailId")
                   String classDetailId, Principal principal) {
        try{
            String username = principal.getName();
            User student = userDetailsService.getUserByUsername(username);
            this.classroomDetailsService.delete(classDetailId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(AppException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-subject")
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest subjectRequest) {
        this.subjectService.create(subjectRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable(value= "subjectId") String subjectId) {
        this.subjectService.delete(subjectId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-teacher/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value= "teacherId") String teacherId) {
        this.teacherService.delete(teacherId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update-role/{teacherId}")
    public ResponseEntity<?> updateRoleTeacher(@PathVariable(value = "teacherId") String teacherId){
        this.teacherService.updateRoleTeacher(teacherId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/down-role/{teacherId}")
    public ResponseEntity<?> downRoleTeacher(@PathVariable(value = "teacherId") String teacherId){
        this.teacherService.downRoleTeacher(teacherId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
