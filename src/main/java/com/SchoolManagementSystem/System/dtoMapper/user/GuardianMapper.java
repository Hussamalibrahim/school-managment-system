package com.SchoolManagementSystem.System.dtoMapper.user;


import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;


public final class GuardianMapper {

    private GuardianMapper() {
    }

    public static GuardianDto toDto(Guardian guardian) {
        return new GuardianDto(
                guardian.getId(),
                guardian.getNationalId(),
                guardian.getFirstName(),
                guardian.getLastName(),
                guardian.getPhone(),
                guardian.getEmail(),
                guardian.getAddress(),
                guardian.getOccupation(),
                guardian.getCreatedAt(),
                guardian.getUpdatedAt(),
                guardian.getDeletedAt()
        );
    }

    public static Guardian toEntity(GuardianDto dto) {
        Guardian guardian = new Guardian();

        guardian.setNationalId(dto.nationalId());
        guardian.setFirstName(dto.firstName());
        guardian.setLastName(dto.lastName());
        guardian.setPhone(dto.phone());
        guardian.setEmail(dto.email());
        guardian.setAddress(dto.address());
        guardian.setOccupation(dto.occupation());

        return guardian;
    }
}