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
    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

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
    private LocalDate dob;

    @ColumnDefault("1")
    @Column(name = "active")
    private boolean active;

    @Size(max = 45)
    @Column(name = "role", length = 45)
    private String role;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        User.serialVersionUID = serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

}
