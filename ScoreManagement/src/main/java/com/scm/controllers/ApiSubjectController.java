package com.scm.controllers;

import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.dto.responses.RegisterSubjectResponse;
import com.scm.exceptions.AppException;
import com.scm.mapper.ClassroomSubjectMapper;
import com.scm.pojo.User;
import com.scm.services.ClassroomSubjectService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/secure/user")
public class ApiSubjectController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ClassroomSubjectService classroomSubjectService;

    @Autowired
    private ClassroomSubjectMapper classroomSubjectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> registerSubject(@RequestBody ClassroomSubjectRequest request, Principal principal) {
        try{
            String username = principal.getName();
            User student = userDetailsService.getUserByUsername(username);
            ClassroomSubjectResponse csr = this.classroomSubjectService.create(request, String.valueOf(student.getId()));
            return new ResponseEntity<>(csr, HttpStatus.OK);
        }
        catch(AppException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{classroomSubjectId}")
    public ResponseEntity<?> registerSubject(@PathVariable(value = "classroomSubjectId")
                                                 String classroomSubjectId, Principal principal) {
        try{
            String username = principal.getName();
            User student = userDetailsService.getUserByUsername(username);
            this.classroomSubjectService.delete(classroomSubjectId, String.valueOf(student.getId()));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(AppException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
