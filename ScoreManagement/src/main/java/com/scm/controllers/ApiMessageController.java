//package com.scm.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.scm.dto.requests.ChatMessageRequest;
//import com.scm.pojo.User;
//import com.scm.services.MessageService;
//import com.scm.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//
//@RestController
//public class ApiMessageController {
//    @Autowired
//    private MessageService messageService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/get-message/{conversationId}")
//    public ResponseEntity<?> getMessages(@PathVariable(value="conversationId") String conversationId,
//                                         Principal principal) {
//        String username = principal.getName();
//        User u = userService.getUserByUsername(username);
//        return ResponseEntity.ok(messageService.getMessages(conversationId, u.getId().toString()));
//    }
//
//    @PostMapping("/create-message/{conversationId}")
//    public ResponseEntity<?> create(@PathVariable(value = "conversationId") String conversationId,
//                                    @RequestBody ChatMessageRequest request,
//                                    Principal principal) throws JsonProcessingException {
//        String username = principal.getName();
//        User u = userService.getUserByUsername(username);
//        return ResponseEntity.ok(messageService.create(request, conversationId, u.getId().toString()));
//    }
//}
