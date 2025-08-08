package com.scm.controllers.admin;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.Subject;
import com.scm.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/subject-form")
    public String createSubject(@ModelAttribute(value="subject") SubjectRequest subject, Model model) {
        SubjectResponse s= this.subjectService.create(subject);
        model.addAttribute("subject",s);
        return "subject";
    }

    @GetMapping("/subject-form")
    public String showSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject-form";
    }


    @GetMapping("/subject")
    public String getAllSubject(@RequestParam(value = "page", defaultValue = "1") String page ,Model model) {
        List<SubjectResponse> listSubjects = this.subjectService.getAllSubjects(page);
        model.addAttribute("subjects", listSubjects);
        return "subject";
    }
}


