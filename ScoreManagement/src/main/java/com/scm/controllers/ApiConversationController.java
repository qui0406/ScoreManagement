package com.scm.controllers;

import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.pojo.User;
import com.scm.services.ConversationService;
import com.scm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/secure")
@Slf4j
public class ApiConversationController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/create-conversation/{classDetailId}")
    public ResponseEntity<?>  createConversation(@RequestBody ConversationRequest request,
                                                 @PathVariable(value = "classDetailId") String classDetailId,
                                                 Principal principal) {
        String username = principal.getName();
        User user = userDetailsService.getUserByUsername(username);
        this.conversationService.create(request, classDetailId, user.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete-conversation/{conversationId}")
    public ResponseEntity<?> deleteConversation(@PathVariable(value = "conversationId") String conversationId,
                                                Principal principal) {
        String username = principal.getName();
        User user = userDetailsService.getUserByUsername(username);
        this.conversationService.delete(conversationId, user.getId().toString());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
