package com.scm.controllers.admin;

import com.scm.dto.requests.LoginRequest;
import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.User;
import com.scm.services.TeacherService;
import com.scm.services.UserService;
import com.scm.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class UserAdminController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @GetMapping("/dashboard")
    public String users(Model model,
                        @RequestParam(value = "page", defaultValue = "1") String page,
                        HttpSession session) {
        String token = (String) session.getAttribute("authToken");
        List<TeacherResponse> teachersNormals = this.teacherService.getAllTeachersByRole("ROLE_TEACHER", page);
        List<TeacherResponse> teachersSupers = this.teacherService.getAllTeachersByRole("ROLE_TEACHER_SUPER", page);

        model.addAttribute("teachersNormals", teachersNormals);
        model.addAttribute("teachersSupers", teachersSupers);
        model.addAttribute("token", token);
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}