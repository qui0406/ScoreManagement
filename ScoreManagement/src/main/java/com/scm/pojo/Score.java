package com.scm.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "class_subject_id", nullable = false)
    private ClassroomSubject classSubject;

    @ManyToOne
    @JoinColumn(name = "score_type_id", nullable = false)
    private ScoreType scoreType;


    @Column(name = "score", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "active", nullable = false)
    private Boolean active = false;
}