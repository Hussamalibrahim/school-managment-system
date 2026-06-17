package com.SchoolManagementSystem.System.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum Gender {
    Male("Male"),
    Female("Female");
    private final String value;
}
