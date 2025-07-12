package com.scm.controllers;

import com.scm.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
//    @GetMapping("/user")
//    public String user() {
//        return "user";
//    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user) {
        // Xử lý logic register ở đây
        // Có thể redirect về trang login sau khi đăng ký thành công
        return "redirect:/login";
    }
}
