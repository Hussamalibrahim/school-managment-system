package com.SchoolManagementSystem.system.entity.Auth;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "auth_users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_email_school",
                        columnNames = {
                                "email",
                                "school_id"
                        }
                )
        }
)
@Getter
@Setter
public class AuthUser extends SchoolEntity {

    @Column(name = "email", nullable = false)
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