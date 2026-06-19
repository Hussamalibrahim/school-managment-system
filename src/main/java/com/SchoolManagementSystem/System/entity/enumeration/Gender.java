package com.SchoolManagementSystem.System.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");
    private final String value;
}
