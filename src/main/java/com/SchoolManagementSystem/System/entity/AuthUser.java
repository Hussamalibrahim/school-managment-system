package com.SchoolManagementSystem.System.entity;

import com.SchoolManagementSystem.System.entity.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "auth_users")
@Getter
@Setter
public class AuthUser extends BaseEntity {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role; // TEACHER, STUDENT, GUARDIAN...

    @Column(name = "ref_id")
    private Long refId;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}