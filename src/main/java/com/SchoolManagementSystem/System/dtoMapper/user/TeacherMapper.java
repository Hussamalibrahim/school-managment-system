package com.SchoolManagementSystem.System.dtoMapper.user;

import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.entity.user.Teacher;

public final class TeacherMapper {

    private TeacherMapper() {
    }

    public static TeacherDto toDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                teacher.getCreatedAt(),
                teacher.getUpdatedAt(),
                teacher.getDeletedAt(),
                teacher.getNationalId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getPhone(),
                teacher.getEmail(),
                teacher.getAddress(),
                teacher.getStatus(),
                teacher.getHireDate(),
                teacher.getSpecialization()
        );
    }

    public static Teacher toEntity(TeacherDto dto) {
        Teacher teacher = new Teacher();

        teacher.setNationalId(dto.nationalId());
        teacher.setFirstName(dto.firstName());
        teacher.setLastName(dto.lastName());
        teacher.setPhone(dto.phone());
        teacher.setEmail(dto.email());
        teacher.setAddress(dto.address());
        teacher.setStatus(dto.status());
        teacher.setHireDate(dto.hireDate());
        teacher.setSpecialization(dto.specialization());

        return teacher;
    }
}