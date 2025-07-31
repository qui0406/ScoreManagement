package com.scm.controllers;

import com.scm.dto.requests.ClassroomRequest;
import com.scm.dto.requests.CreateClassDetailsRequest;
import com.scm.dto.requests.SubjectRequest;
import com.scm.exceptions.AppException;
import com.scm.pojo.User;
import com.scm.services.ClassroomDetailsService;
import com.scm.services.ClassroomService;
import com.scm.services.SubjectService;
import com.scm.services.UserService;
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

    //Tao lop hoc
    @PostMapping("/create/classroom")
    public ResponseEntity<?> createClassroom(@RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(classroomService.create(request));
    }

    //Xoa lop hoc
    @DeleteMapping("/delete/{classroomId}")
    public ResponseEntity<?> deleteClassroom(@PathVariable(value= "classroomId") String classroomId) {
        this.classroomService.delete(classroomId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    //Tao mon hoc cho lop hoc do
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

    //Xoa mon hoc cho lop hoc do
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

}
