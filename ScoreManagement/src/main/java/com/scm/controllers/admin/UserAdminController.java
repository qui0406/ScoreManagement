package com.scm.controllers.admin;

import com.scm.pojo.User;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserPage(Model model) {
        model.addAttribute("users", this.userService.getAllUsers());
        return "admin/users";  // Trả về view Thymeleaf
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";  // Trả về view login.html
    }


    @GetMapping("/my-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getCurrentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/admin";
        }
        String username = userDetails.getUsername();
        User user = this.userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "admin/account";
    }



}
