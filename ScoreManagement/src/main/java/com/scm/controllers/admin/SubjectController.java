package com.scm.controllers.admin;

import com.scm.dto.requests.SubjectRequest;
import com.scm.dto.responses.SubjectResponse;
import com.scm.pojo.Faculty;
import com.scm.pojo.Semester;
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
//    private FacultySer
    @PostMapping("/subject-form")
    public String createSubject(@ModelAttribute(value="subject") SubjectRequest subject) {
        this.subjectService.create(subject);
        return "redirect:/admin/subject";
    }
    @GetMapping("/subject-form")
    public String showSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());

        // Lấy danh sách khoa và học kỳ (từ DB hoặc service)
//        List<Faculty> faculties = facultyService.findAll();
//        List<Semester> semesters = semesterService.findAll();

//        model.addAttribute("faculties", faculties);
//        model.addAttribute("semesters", semesters);

        return "subject-form";
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public String deleteSubject(@PathVariable("subjectId") int subjectId) {
        this.deleteSubject(subjectId);
        return "dashboard";
    }

    @GetMapping("/subject")
    public String getAllSubject(@RequestParam(value = "page", defaultValue = "1") String page ,Model model) {
        List<SubjectResponse> listSubjects = this.subjectService.getAllSubjects(page);
        model.addAttribute("subjects", listSubjects);
        return "subject";
    }
}


