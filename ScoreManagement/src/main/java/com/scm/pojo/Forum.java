package com.scm.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "forum")
public class Forum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="class_detail_id", nullable = false)
    private ClassDetails classDetails;

    @ManyToOne
    @JoinColumn(name= "user_created_id", nullable = false)
    private User user;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
