package com.scm.controllers;

import com.scm.dto.requests.*;
import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.dto.responses.WriteScoreStudentPDFResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.helpers.CSVHelper;
import com.scm.helpers.PDFHelper;
import com.scm.pojo.User;
import com.scm.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/secure/teacher")
@Slf4j
public class ApiTeacherSuperController {
    @Value("${spring.send_grid.from_email}")
    private String sendGridFromEmail;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SendGridMailService sendGridMailService;

    @Autowired
    private ScoreTypeService scoreTypeService;

    @Autowired
    private ScoreStudentService scoreStudentService;

    @PostMapping(path = "/upload-scores/{classDetailId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadScores(@RequestParam(value = "file") MultipartFile file,
                                          @PathVariable(value="classDetailId") String  classDetailId,
                                          Principal principal) {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_IS_EMPTY);        }
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            List<ReadFileCSVRequest> r = CSVHelper.parseScoreCSV(file);
            List<String> listMssv = new ArrayList<>();

            if (r == null) {
                throw new AppException(ErrorCode.FILE_IS_EMPTY);
            }
            for(ReadFileCSVRequest x : r){
                listMssv.add(x.getMssv());
            }

            if(!studentService.getAllMssvByClass(classDetailId).equals(listMssv)){
                throw new AppException(ErrorCode.LIST_STUDENT_NOT_SUITABLE);
            }

            List<ListScoreStudentRequest> request = new ArrayList<>();

            for (ReadFileCSVRequest readFileCSVRequest : r) {
                ListScoreStudentRequest tmp = new ListScoreStudentRequest();
                tmp.setStudentId(studentService.getIdByMssv(readFileCSVRequest.getMssv()));
                tmp.setClassDetailId(classDetailId);
                tmp.setScores(readFileCSVRequest.getScores());
                request.add(tmp);
            }
            this.scoreService.addListScoreAllStudents(request, teacher.getId().toString());
        }
        catch (AppException ex){
            throw new AppException(ErrorCode.READ_FILE_ERROR);
        }
        return ResponseEntity.ok("value: Successfully");
    }

    @PostMapping("/export-scores/{classDetailId}")
    public ResponseEntity<String> exportScores(@PathVariable(value="classDetailId") String classDetailId,
                                               Principal principal) throws Exception {
        try{
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            List<WriteScoreStudentPDFResponse> request = this.scoreStudentService.listScorePDF(classDetailId, teacher.getId().toString());

            List<ScoreTypeResponse> listScoreType = this.scoreTypeService.getScoreTypesByClassDetails(classDetailId);
            List<String> listScoreTypeName = listScoreType.stream()
                    .map(ScoreTypeResponse::getScoreTypeName)
                    .collect(Collectors.toList());

            PDFHelper.exportScoreListToPDF(request, listScoreTypeName);
            return ResponseEntity.ok("value: Successfully");
        }
        catch (AppException ex){
            return ResponseEntity.badRequest().body("value:" + ex.getErrorCode().getMessage());
        }
    }

        @PostMapping("/score/block-score/{classDetailId}")
    public ResponseEntity<?> blockScore(@PathVariable(value="classDetailId") String classDetailId,
                                        Principal principal) {
        try {
            String teacherName = principal.getName();
            User teacher = userDetailsService.getUserByUsername(teacherName);
            this.scoreService.blockScore(teacher.getId().toString(), classDetailId);


            EmailRequest emailRequest = new EmailRequest();
            List<Recipient> listRecipients= studentService.getAllRecipientStudentsByClass(classDetailId);
            emailRequest.setSubject("Blocked Students");
            emailRequest.setContent("This student has been blocked.");
            emailRequest.setSender(new Sender(
                    teacherName, sendGridFromEmail
            ));
            emailRequest.setTo(listRecipients);
            this.sendGridMailService.sendMail(emailRequest);


            return ResponseEntity.ok("value: Successfully");
        }catch (AppException e) {
            throw new AppException(ErrorCode.BLOCK_SCORE_ERROR);
        }
    }

    @GetMapping("/score/get-status/{classDetailId}")
    public ResponseEntity<?> getStatus(@PathVariable(value="classDetailId") String classDetailId) {
        return ResponseEntity.ok(this.scoreService.getStatusBlock(classDetailId));
    }
}
