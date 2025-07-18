package com.scm.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student extends User {

    @Column(unique = true)
    String mssv;

    @Column(name = "school_year")
    Date schoolYear;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
}
