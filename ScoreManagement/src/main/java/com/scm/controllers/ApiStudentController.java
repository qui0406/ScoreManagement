package com.scm.controllers;

import com.scm.dto.requests.UpdateUserRequest;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.ScoreStudentService;
import com.scm.services.SubjectService;
import com.scm.services.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/secure/user")
@Builder
@Slf4j
public class ApiStudentController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @Autowired
    private SubjectService subjectService;


    @PutMapping(path = "/update-profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ModelAttribute UpdateUserRequest request,
                                    MultipartFile avatar,
                                    Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chua dang nhap!");
        }

        String username = principal.getName();
        User currentUser = this.userDetailsService.getUserByUsername(username);

        return ResponseEntity.ok(this.userDetailsService.update(request, currentUser.getId().toString(), avatar));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return ResponseEntity.ok(this.userDetailsService.getProfile(principal));
    }

    @GetMapping("/my-score/{classDetailId}")
    public ResponseEntity<?> getMyScore(Principal principal,
                                        @PathVariable(value="classDetailId") String classDetailId) {
        String name = principal.getName();
        User user = this.userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.scoreStudentService.getScoreByStudentAndClassWhenBlockScore(user.getId().toString(), classDetailId));
    }

    @GetMapping("/list-subject/semester/{semesterId}")
    public ResponseEntity<?> getSubjectsInSemester(Principal principal,
                                                   @PathVariable(value="semesterId") String semesterId) {
        String name = principal.getName();
        User user = this.userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.subjectService.getAllSubjectsBySemester(user.getId().toString(), semesterId));
    }
}
