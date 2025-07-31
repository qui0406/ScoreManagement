package com.scm.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "conversation")
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private  Student student;

    private String content;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
