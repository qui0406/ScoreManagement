package com.scm.controllers;

import com.scm.dto.requests.EmailRequest;
import com.scm.dto.requests.Recipient;
import com.scm.services.SendGridMailService;
import com.scm.services.StudentService;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/email")
public class ApiSendGridController {
    @Autowired
    private SendGridMailService sendGridMailService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/send-notification/{classDetailId}")
    public ResponseEntity<?> sendNotification(@PathVariable(value="classDetailId") String classDetailId,
                                              @RequestBody EmailRequest request) {
        try{
            List<Recipient> listRecipients= studentService.getAllRecipientStudentsByClass(classDetailId);
            request.setTo(listRecipients);
            return ResponseEntity.ok(this.sendGridMailService.sendMail(request));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
