package com.scm.controllers.admin;

import com.scm.dto.requests.TeacherRegisterRequest;
import com.scm.dto.responses.TeacherResponse;
import com.scm.services.TeacherService;
import com.scm.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userDetailsService;

//    @GetMapping("/teachers")
//    public String listTeachers(@RequestParam Map<String,String> map, Model model) {
//        model.addAttribute("teachers", this.teacherService.getAllTeachersByRole(map));
//        return "dashboard";
//    }


    @GetMapping("/register-teacher")
    public String showRegisterTeacherForm(Model model) {
        model.addAttribute("teacher", new TeacherRegisterRequest());
        return "register-teacher";
    }

    @PostMapping("/register-teacher")
    public String registerTeacher(@ModelAttribute("teacher") @Valid TeacherRegisterRequest request,
                                  BindingResult result,
                                  @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                  Model model) {
        if (result.hasErrors()) {
            return "register-teacher";
        }
        TeacherResponse teacher = userDetailsService.registerTeacher(request, avatar);
        model.addAttribute("teacherInfo", teacher);
        return "register-success";
    }

    @GetMapping("/list-teacher-normal")
    public String listTeacherNormal(Model model,
                                    @RequestParam("page") String page, Principal principal) {
        List<TeacherResponse> teachersNormals = this.teacherService.getAllTeachersByRole("ROLE_TEACHER", page);
        model.addAttribute("teachersNormals", teachersNormals);
        return "dashboard";
    }

    @GetMapping("/list-teacher-super")
    public String listTeacherSuper(Model model,
                                    @RequestParam("page") String page, Principal principal) {
        List<TeacherResponse> teachersSupers = this.teacherService.getAllTeachersByRole("ROLE_TEACHER_SUPER", page);
        model.addAttribute("teachersSupers", teachersSupers);
        return "dashboard";
    }

}
