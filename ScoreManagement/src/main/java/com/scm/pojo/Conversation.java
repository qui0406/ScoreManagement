package com.scm.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "conversation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "student_id"}))
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @Column(name="created_at")
    private LocalDateTime createdAt= LocalDateTime.now();
}
