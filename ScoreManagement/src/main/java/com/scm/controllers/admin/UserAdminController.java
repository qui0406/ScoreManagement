package com.scm.controllers.admin;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import com.scm.services.SemesterService;
import com.scm.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String users() {
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}