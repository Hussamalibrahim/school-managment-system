package com.SchoolManagementSystem.system.entity.user;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseUser extends SchoolEntity {

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "hire_date")
    private LocalDate hireDate;
}
