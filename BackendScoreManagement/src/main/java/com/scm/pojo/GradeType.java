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
@Table(name = "grade_type")
public class GradeType {
    @Id
    @Column(name = "grade_type_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "grade_type_name", nullable = false, length = 50)
    private String gradeTypeName;

}