package com.SchoolManagementSystem.system.mapper.user;

import com.SchoolManagementSystem.system.entity.user.Secretary;
import com.SchoolManagementSystem.system.dto.user.SecretaryDto;


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
                secretary.getAddress(),
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
        secretary.setAddress(dto.address());
        secretary.setHireDate(dto.hireDate());

        return secretary;
    }

    public static void updateEntity(Secretary secretary, SecretaryDto dto) {
        secretary.setNationalId(dto.nationalId());
        secretary.setFirstName(dto.firstName());
        secretary.setLastName(dto.lastName());
        secretary.setPhone(dto.phone());
        secretary.setAddress(dto.address());
        secretary.setHireDate(dto.hireDate());
    }
}