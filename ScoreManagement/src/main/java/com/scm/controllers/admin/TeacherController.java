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
                                  @RequestParam(value = "avatar") MultipartFile avatar,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("err", "Đăng kí thất bại");
            return "register-teacher";
        }
        TeacherResponse teacher = userDetailsService.registerTeacher(request, avatar);
        model.addAttribute("teacher", teacher);
        model.addAttribute("successMessage", "Đăng kí thành công");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-role-teacher/{teacherId}")
    public String updateRoleTeacher(Model model, @PathVariable(value="teacherId") String teacherId){
        this.teacherService.updateRoleTeacher(teacherId);
        return "dashboard";
    }
}
