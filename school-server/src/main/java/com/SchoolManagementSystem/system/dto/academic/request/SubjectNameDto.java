package com.SchoolManagementSystem.system.dto.academic.request;


import java.io.Serializable;

public record SubjectNameDto(
        Long id,
        String name
)implements Serializable {
}