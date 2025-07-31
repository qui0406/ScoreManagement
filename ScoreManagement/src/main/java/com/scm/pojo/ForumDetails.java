package com.scm.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "forum_detail")
@Entity
public class ForumDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "forum_id", nullable = false)
    private Forum forum;

    @ManyToOne
    @JoinColumn(name= "user_response_id", nullable = false)
    private User user;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
