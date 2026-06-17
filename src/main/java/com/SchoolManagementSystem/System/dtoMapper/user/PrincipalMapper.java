package com.SchoolManagementSystem.System.dtoMapper.user;

import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.entity.user.Principal;

public final class PrincipalMapper {

    private PrincipalMapper() {
    }

    public static PrincipalDto toDto(Principal principal) {
        return new PrincipalDto(
                principal.getId(),
                principal.getNationalId(),
                principal.getFirstName(),
                principal.getLastName(),
                principal.getPhone(),
                principal.getEmail(),
                principal.getAddress(),
                principal.getStatus(),
                principal.getHireDate(),
                principal.getCreatedAt(),
                principal.getUpdatedAt(),
                principal.getDeletedAt()
        );
    }

    public static Principal toEntity(PrincipalDto dto) {
        Principal principal = new Principal();

        principal.setNationalId(dto.nationalId());
        principal.setFirstName(dto.firstName());
        principal.setLastName(dto.lastName());
        principal.setPhone(dto.phone());
        principal.setEmail(dto.email());
        principal.setAddress(dto.address());
        principal.setStatus(dto.status());
        principal.setHireDate(dto.hireDate());

        return principal;
    }
}