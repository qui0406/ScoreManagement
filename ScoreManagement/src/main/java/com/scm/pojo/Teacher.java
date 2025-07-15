package com.scm.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher extends User {

    @Column(name = "msgv", unique = true)
    String msgv;

    @Column(name="experience")
    String experience;

    @Column(name= "position")
    String position;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    Faculty faculty;
}
