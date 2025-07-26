package com.scm.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "class_subject_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "score_type_id", nullable = false)
    private ScoreType scoreType;

    @ManyToOne
    @JoinColumn(name = "class_subject_id", nullable = false)
    private ClassSubject classSubject;
}
