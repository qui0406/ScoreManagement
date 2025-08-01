package com.scm.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "username", nullable = false, length = 45, unique = true)
    private String username;

    @Size(min = 8, message = "Mật khẩu phải trên 8 kí tự")
    @NotBlank(message = "Không được bỏ trống mục này!")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Email(message = "Email không đúng định dạng")
    @Column(name = "email", nullable = false, length = 100, unique= true)
    private String email;

    @Column(name = "phone_number", length = 15, unique = true)
    private String phone;

    @ColumnDefault("1")
    @Column(name = "gender")
    private boolean gender;

    @Column(name="address")
    private String address;

    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @ColumnDefault("1")
    @Column(name = "active")
    private boolean active;

    @Size(max = 45)
    @Column(name = "role", length = 45)
    private String role;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
}
