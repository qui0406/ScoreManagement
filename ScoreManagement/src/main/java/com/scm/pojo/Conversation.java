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

    public boolean isParticipant(Integer userId) {
        return teacher.getId().equals(userId) || student.getId().equals(userId);
    }

    public Integer getOtherParticipantId(Integer currentUserId) {
        if (teacher.getId().equals(currentUserId)) {
            return student.getId();
        } else if (student.getId().equals(currentUserId)) {
            return teacher.getId();
        }
        throw new IllegalArgumentException("User is not a participant in this conversation");
    }
}
