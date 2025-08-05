package com.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    Sender sender;
    List<Recipient> to;
    String subject;
    String content;
}
