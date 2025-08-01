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
public class ForumRequest {
    private String classDetailId;
    private String userCreatedId;
    private String content;
    private LocalDateTime createdAt= LocalDateTime.now();
}
