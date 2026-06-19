package com.SchoolManagementSystem.System.mapper.user;

import com.SchoolManagementSystem.System.entity.user.Secretary;
import com.SchoolManagementSystem.System.dto.user.SecretaryDto;


public final class SecretaryMapper {

    private SecretaryMapper() {
    }

    public static SecretaryDto toDto(Secretary secretary) {
        return new SecretaryDto(
                secretary.getId(),
                secretary.getNationalId(),
                secretary.getFirstName(),
                secretary.getLastName(),
                secretary.getPhone(),
                secretary.getEmail(),
                secretary.getAddress(),
                secretary.getStatus(),
                secretary.getHireDate(),
                secretary.getCreatedAt(),
                secretary.getUpdatedAt(),
                secretary.getDeletedAt()
        );
    }

    public static Secretary toEntity(SecretaryDto dto) {
        Secretary secretary = new Secretary();

        secretary.setNationalId(dto.nationalId());
        secretary.setFirstName(dto.firstName());
        secretary.setLastName(dto.lastName());
        secretary.setPhone(dto.phone());
        secretary.setEmail(dto.email());
        secretary.setAddress(dto.address());
        secretary.setStatus(dto.status());
        secretary.setHireDate(dto.hireDate());

        return secretary;
    }
}