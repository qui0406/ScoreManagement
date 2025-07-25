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
@CrossOrigin
public class UserAdminController {
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserPage(Model model) {
        model.addAttribute("users", this.userDetailsService.getAllUsers());
        return "admin/users";
    }


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/my-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getCurrentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/admin";
        }
        String username = userDetails.getUsername();
        User user = this.userDetailsService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "admin/account";
    }

    @PostMapping("/create-semester")
    public String createSemester(@ModelAttribute(value="semester") SemesterRequest semesterRequest) {
        this.semesterService.create(semesterRequest);
        return "redirect:/admin/createSemester";
    }
}
