package com.scm.controllers;

import com.scm.dto.responses.UserResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.ScoreStudentService;
import com.scm.services.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

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

    @PostMapping(path = "/update-profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestParam Map<String, String> params,
                                    @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        User u = this.userDetailsService.registerStudent(params, avatar);
        UserResponse userResponse = userMapper.toUserResponse(u);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return ResponseEntity.ok(this.userDetailsService.getProfile(principal));
    }
}
