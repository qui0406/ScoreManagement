package com.scm.controllers;

import com.scm.dto.requests.UpdateUserRequest;
import com.scm.dto.responses.UserResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.ScoreStudentService;
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

//    @PutMapping(path = "/update-profile",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> update(@RequestBody UpdateUserRequest request,
//                                    Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chua dang nhap!");
//        }
//
//        String username = principal.getName();
//        User currentUser = this.userDetailsService.getUserByUsername(username);
//
//        if(this.userDetailsService.checkExistUsername(username)){
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }
//        if(this.userDetailsService.checkExistEmail(currentUser.getEmail())){
//            throw new AppException(ErrorCode.EMAIL_EXISTED);
//        }
//        return ResponseEntity.ok(this.userDetailsService.update(request));
//    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return ResponseEntity.ok(this.userDetailsService.getProfile(principal));
    }
}
