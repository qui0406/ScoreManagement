package com.scm.controllers.staff;

import com.scm.dto.requests.SemesterRequest;
import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.TeacherResponse;
import com.scm.mapper.UserMapper;
import com.scm.pojo.Teacher;
import com.scm.pojo.User;
import com.scm.services.SemesterService;
import com.scm.services.SubjectService;
import com.scm.services.TeacherService;
import com.scm.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class StaffController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SubjectService subjectService;


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserPage(Model model) {
        model.addAttribute("users", this.userDetailsService.getAllUsers());
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/my-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getCurrentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/";
        }
        String username = userDetails.getUsername();
        User user = this.userDetailsService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/account";
    }

    @PostMapping("/create-semester")
    public String createSemester(@ModelAttribute(value="semester") SemesterRequest semesterRequest) {
        this.semesterService.create(semesterRequest);
        return "redirect:/createSemester";
    }

    @PostMapping(path = "/register-teacher",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createTeacher(@ModelAttribute(value="teacher")
            @Valid @RequestParam Map<String, String> params,
                                           @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        Teacher u = this.userDetailsService.registerTeacher(params, avatar);
        TeacherResponse teacherResponse = userMapper.toTeacherResponse(u);
        return "redirect:/infoTeacher";
    }

    @PostMapping("/create-subject")
    public String createSubject(@ModelAttribute(value="subject") SubjectRequest subjectRequest) {
        this.subjectService.create(subjectRequest);
        return "redirect:/infoSubject";
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public void deleteSubject(@PathVariable(value= "subjectId") String subjectId) {
        this.subjectService.delete(subjectId);
    }

}
