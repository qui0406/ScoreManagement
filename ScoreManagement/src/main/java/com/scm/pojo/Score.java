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

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_subject_id", nullable = false)
    private ClassroomSubject classSubject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "score_type_id", nullable = false)
    private ScoreType scoreType;


    @Column(name = "score", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;
}