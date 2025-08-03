package com.scm.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private int id;
    private String senderUsername;
    private String recipientUsername;

    private String content;
}