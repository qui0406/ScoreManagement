package com.scm.dto.responses;

import com.scm.dto.StudentMessageDTO;
import com.scm.dto.TeacherMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private String id;
    private StudentMessageDTO studentId;
    private TeacherMessageDTO teacherId;
    private LocalDateTime createdAt;
}
