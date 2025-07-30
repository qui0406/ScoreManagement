package com.scm.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_detail_score_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailsScoreType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "score_type_id", nullable = false)
    private ScoreType scoreType;

    @ManyToOne
    @JoinColumn(name = "class_detail_id", nullable = false)
    private ClassDetails classDetails;
}
