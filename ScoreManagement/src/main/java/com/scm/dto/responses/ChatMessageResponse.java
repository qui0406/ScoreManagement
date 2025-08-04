package com.scm.dto.responses;

import com.scm.dto.SenderDTO;
import com.scm.pojo.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private String id;
    private Conversation conversationId;
    private String content;
    private LocalDateTime createdDate;

    private boolean isMe= false;

    private String sender;
}
