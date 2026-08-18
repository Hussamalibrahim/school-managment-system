package com.SchoolManagementSystem.system.entity.Auth;

import com.SchoolManagementSystem.system.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuth extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;
}
