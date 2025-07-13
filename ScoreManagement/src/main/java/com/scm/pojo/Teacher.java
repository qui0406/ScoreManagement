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
    private String msgv;

    @Column(name = "experience")
    private String experience;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom; // Đã sửa từ ClassRoom

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
}
