package com.scm.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message_test")
public class MessageTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserTest sender;

    @ManyToOne
    private UserTest receiver;

    private String content;
    private LocalDateTime timestamp;
}
