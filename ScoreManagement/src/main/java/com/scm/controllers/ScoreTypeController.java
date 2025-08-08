package com.scm.controllers;

import com.scm.dto.requests.ScoreTypeRequest;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.pojo.User;
import com.scm.services.ScoreTypeService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/secure/teacher")
public class ScoreTypeController {
    @Autowired
    private ScoreTypeService scoreTypeService;

    @Autowired
    private UserService userDetailsService;

    @GetMapping("/class-subject/score-types")
    public ResponseEntity<List<ScoreTypeResponse>> getScoreTypes() {
        try {
            List<ScoreTypeResponse> scoreTypes = this.scoreTypeService.getScoreTypes();
            return new ResponseEntity<>(scoreTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class-subject/score-types/{classDetailId}")
    public ResponseEntity<List<ScoreTypeResponse>> getScoreTypesByClassSubject(
            @PathVariable(value="classDetailId")
            String classDetailId) {
        try {
            List<ScoreTypeResponse> scoreTypes = this.scoreTypeService
                    .getScoreTypesByClassDetails(classDetailId);
            return new ResponseEntity<>(scoreTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/class-subject/score-type/{classDetailId}/add")
    public ResponseEntity<?> addScoreType(
            @PathVariable(value = "classDetailId") String classDetailId,
            @RequestBody ScoreTypeRequest scoreTypeRequest,
            Principal principal) {
        String teacherName = principal.getName();
        User teacherId = userDetailsService.getUserByUsername(teacherName);
        this.scoreTypeService.addScoreType(classDetailId, scoreTypeRequest, teacherId.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/class-subject/score-type/{classDetailId}/delete")
    public ResponseEntity<?> deleteScoreType(
            @PathVariable(value = "classDetailId") String classDetailId,
            @RequestParam(value = "scoreTypeId") String scoreTypeId,
            Principal principal) {
        String teacherName = principal.getName();
        User teacherId = userDetailsService.getUserByUsername(teacherName);
        this.scoreTypeService.deleteScoreType(classDetailId, scoreTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
