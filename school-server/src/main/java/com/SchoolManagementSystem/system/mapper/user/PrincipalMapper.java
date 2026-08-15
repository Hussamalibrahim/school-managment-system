package com.SchoolManagementSystem.system.mapper.user;

import com.SchoolManagementSystem.system.dto.user.PrincipalDto;
import com.SchoolManagementSystem.system.entity.user.Principal;
import com.SchoolManagementSystem.system.security.dto.RegisterRequest;

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
                principal.getAddress(),
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
        principal.setAddress(dto.address());
        principal.setHireDate(dto.hireDate());

        return principal;
    }

    public static Principal fromRegisterRequest(RegisterRequest request) {

        Principal principal = new Principal();

        principal.setNationalId(request.nationalId());
        principal.setFirstName(request.firstName());
        principal.setLastName(request.lastName());

        return principal;
    }

    public static Principal updateEntity(Principal principal, PrincipalDto dto) {
        principal.setNationalId(dto.nationalId());
        principal.setFirstName(dto.firstName());
        principal.setLastName(dto.lastName());
        principal.setPhone(dto.phone());
        principal.setAddress(dto.address());
        principal.setHireDate(dto.hireDate());
        return principal;
    }
}