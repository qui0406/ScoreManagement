package com.scm.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sender {
    private String name;
    private String email;
}
