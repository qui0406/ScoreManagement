package com.scm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderDTO {
    private String senderId;
    private String senderUsername;
    private String avatar;
}
