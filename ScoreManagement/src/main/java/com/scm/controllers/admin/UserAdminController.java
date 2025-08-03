package com.scm.controllers.admin;

import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.services.TeacherService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String users(Model model,
                        @RequestParam(value = "page", defaultValue = "1") String page,
                        Principal principal) {
        List<TeacherResponse> teachersNormals = this.teacherService.getAllTeachersByRole("ROLE_TEACHER", page);
        List<TeacherResponse> teachersSupers = this.teacherService.getAllTeachersByRole("ROLE_TEACHER_SUPER", page);

        model.addAttribute("teachersNormals", teachersNormals);
        model.addAttribute("teachersSupers", teachersSupers);
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}