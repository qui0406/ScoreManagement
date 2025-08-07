package com.scm.controllers.admin;

import com.scm.dto.requests.FacultyRequest;
import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.FacultyResponse;
import com.scm.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @PostMapping("/subject-form")
    public String createSubject(@ModelAttribute(value="faculty") FacultyRequest request, Model model) {
        FacultyResponse f = this.facultyService.create(request);
        model.addAttribute("faculty",f);
        return "subject";
    }
}
