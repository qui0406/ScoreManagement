package com.scm.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "subject_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;

}