/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.controllers;

import com.scm.dto.requests.GradeRequest;
import com.scm.dto.requests.GradeTypeRequest;
import com.scm.services.ClassroomSubjectService;
import com.scm.services.StudentService;
import com.scm.services.GradeService;
import com.scm.services.GradeTypeService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class TeacherController {
    @Autowired
    private ClassroomSubjectService classroomSubjectService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private GradeTypeService gradeTypeService;

    //Hiển thị danh sách lớp môn giảng viên đang dạy
    @GetMapping("/teacher/my-classes")
    public String listMyClasses(Model model, Principal principal) {
        String teacherId = principal.getName();
        model.addAttribute("classroomSubjects", this.classroomSubjectService.getClassroomSubjectsByTeacherId(teacherId));
        return "teacher/my-classes";
    }

    // Hiển thị chi tiết lớp môn
    @GetMapping("/teacher/class-subject/{classSubjectId}")
    public String viewClassSubject(Model model, @PathVariable Integer classSubjectId) {
        // Thông tin chi tiết lớp môn
        model.addAttribute("classSubjectDetails", this.classroomSubjectService.getClassroomSubjectDetails(classSubjectId));

        // Danh sách sinh viên
        model.addAttribute("students", this.studentService.getStudentsByClassSubjectId(classSubjectId));

        // Danh sách loại điểm
        model.addAttribute("gradeTypes", this.gradeTypeService.getGradeTypesByClassSubject(classSubjectId));

        // Danh sách điểm đã nhập
        model.addAttribute("grades", this.gradeService.getGradesByClassSubjectId(classSubjectId));

        // Form objects
        model.addAttribute("gradeRequest", new GradeRequest());
        model.addAttribute("gradeTypeRequest", new GradeTypeRequest());

        return "teacher/class-subject-detail";
    }

    // Thêm loại điểm mới
    @PostMapping("/teacher/class-subject/{classSubjectId}/grade-types")
    public String addGradeType(@PathVariable Integer classSubjectId,
                              @ModelAttribute GradeTypeRequest gradeTypeRequest) {
        try {
            if (!this.gradeTypeService.canAddMoreGradeTypes(classSubjectId)) {
                return "redirect:/teacher/class-subject/" + classSubjectId + "?error=maxGradeTypes";
            }

            this.gradeTypeService.addGradeTypeToClassSubject(gradeTypeRequest, classSubjectId);
            return "redirect:/teacher/class-subject/" + classSubjectId + "?success=gradeTypeAdded";
        } catch (Exception e) {
            return "redirect:/teacher/class-subject/" + classSubjectId + "?error=addGradeTypeFailed";
        }
    }

    //Nhập điểm cho sinh viên
    @PostMapping("/teacher/grades")
    public String addOrUpdateGrade(@ModelAttribute GradeRequest gradeRequest,
                                  Principal principal,
                                  @RequestParam Integer classSubjectId) {
        try {
            String teacherId = principal.getName();
            this.gradeService.addOrUpdateGrade(gradeRequest, teacherId);
            return "redirect:/teacher/class-subject/" + classSubjectId + "?success=gradeAdded";
        } catch (Exception e) {
            return "redirect:/teacher/class-subject/" + classSubjectId + "?error=addGradeFailed";
        }
    }
}
