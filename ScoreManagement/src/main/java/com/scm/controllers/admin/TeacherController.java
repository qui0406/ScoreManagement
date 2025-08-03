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
        model.addAttribute("teacher", teacher);
        return "dashboard";
    }

    @GetMapping("/dashboard/teachers")
    public String dashboard(Model model,
                            @RequestParam(value = "page", defaultValue = "1") String page,
                            Principal principal) {
        // Lấy danh sách giảng viên thường
        List<TeacherResponse> teachersNormals = this.teacherService.getAllTeachersByRole("ROLE_TEACHER", page);
        // Lấy danh sách giảng viên quản lý
        List<TeacherResponse> teachersSupers = this.teacherService.getAllTeachersByRole("ROLE_TEACHER_SUPER", page);

        // Thêm vào model
        model.addAttribute("teachersNormals", teachersNormals);
        model.addAttribute("teachersSupers", teachersSupers);

        return "dashboard"; // Trả về trang dashboard
    }

//    @GetMapping("/list-teacher-normal")
//    public String listTeacherNormal(Model model,
//                                    @RequestParam(value = "page", defaultValue = "1") String page,
//                                    Principal principal) {
//        List<TeacherResponse> teachersNormals = this.teacherService.getAllTeachersByRole("ROLE_TEACHER", page);
//        model.addAttribute("teachersNormals", teachersNormals);
//        return "teacher-normal-list"; // Trả về đúng template
//    }
//
//    @GetMapping("/list-teacher-super")
//    public String listTeacherSuper(Model model,
//                                   @RequestParam(value = "page", defaultValue = "1") String page,
//                                   Principal principal) {
//        List<TeacherResponse> teachersSupers = this.teacherService.getAllTeachersByRole("ROLE_TEACHER_SUPER", page);
//        model.addAttribute("teachersSupers", teachersSupers);
//        return "teacher-super-list"; // Trả về đúng template
//    }

}
