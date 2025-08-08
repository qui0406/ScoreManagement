package com.scm.controllers;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.requests.ForumRequest;
import com.scm.mapper.ForumDetailsMapper;
import com.scm.pojo.User;
import com.scm.repositories.ForumDetailsRepository;
import com.scm.services.ForumDetailsService;
import com.scm.services.ForumService;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/secure")
public class ApiForumController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumDetailsService forumDetailsService;

    @PostMapping("/create-forum")
    public ResponseEntity<?> createForum(@RequestBody ForumRequest request, Principal principal) {
        String name =  principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        this.forumService.create(request, u.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all-forum/{classDetailId}")
    public ResponseEntity<?> getAllForum(@PathVariable(value ="classDetailId") String classDetailId,  Principal principal) {
        String name =   principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.forumService.getAllForumsByClassDetailId(classDetailId, u.getId().toString()));
    }

    @DeleteMapping("/delete-forum/{forumId}")
    public ResponseEntity<?> deleteForum(@PathVariable(value = "forumId") String forumId,
                                         Principal principal) {
        String name =  principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        this.forumService.delete(forumId, u.getId().toString());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/forum-reply/{forumId}")
    public ResponseEntity<?> replyForum(@RequestBody ForumDetailsRequest request,
                                        @PathVariable(value="forumId") String forumId,
                                        Principal principal) {
        String name =  principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        this.forumDetailsService.create(request, forumId, u.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/forum-reply/update/{forumDetailId}")
    public ResponseEntity<?> updateForum(@RequestBody ForumDetailsRequest request,
                                         Principal principal, @PathVariable(value="forumDetailId") String forumId) {
        String name =  principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        this.forumDetailsService.update(request, forumId, u.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/forum-reply/delete/{forumDetailId}")
    public ResponseEntity<?> deleteReplyForum(@PathVariable(value ="forumDetailId") String forumId, Principal principal) {
        String name =  principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        this.forumDetailsService.delete(forumId, u.getId().toString());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get-all-reply-forum/{forumId}")
    public ResponseEntity<?> getAllReplyForum(@PathVariable(value = "forumId") String forumId,
                                              Principal principal) {
        String name = principal.getName();
        User u = userDetailsService.getUserByUsername(name);
        return ResponseEntity.ok(this.forumDetailsService.getAllForumByForumId(forumId));
    }
}
