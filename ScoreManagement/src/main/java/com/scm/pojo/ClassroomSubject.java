package com.scm.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classroom_subject")
public class  ClassroomSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_subject_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Size(max = 20)
    @Column(name = "semester", length = 20)
    private String semester;
}