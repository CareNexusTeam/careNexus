package com.cts.careNexus.userIdentity.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private UserRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "department_id")
    private Long departmentId;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status = UserStatus.Active;

}
