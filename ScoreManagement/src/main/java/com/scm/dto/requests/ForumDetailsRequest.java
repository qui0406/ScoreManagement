package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumDetailsRequest {
    private String forumId;
    private String message;
    private LocalDateTime createdAt =  LocalDateTime.now();
    private String userResponseId;
}
