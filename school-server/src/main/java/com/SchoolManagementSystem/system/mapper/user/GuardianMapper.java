package com.SchoolManagementSystem.system.mapper.user;


import com.SchoolManagementSystem.system.dto.user.request.AuthRequestGuardian;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import com.SchoolManagementSystem.system.dto.user.GuardianDto;


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
        guardian.setAddress(dto.address());
        guardian.setOccupation(dto.occupation());

        return guardian;
    }

    public static Guardian fromAuthRequestGuardian(AuthRequestGuardian dto) {
        Guardian guardian = new Guardian();

        guardian.setNationalId(dto.nationalId());
        guardian.setFirstName(dto.firstName());
        guardian.setLastName(dto.lastName());
        guardian.setPhone(dto.phone());
        guardian.setAddress(dto.address());
        guardian.setOccupation(dto.occupation());
        return guardian;
    }

    public static Guardian updateEntity(Guardian guardian, GuardianDto dto){
        guardian.setNationalId(dto.nationalId());
        guardian.setFirstName(dto.firstName());
        guardian.setLastName(dto.lastName());
        guardian.setPhone(dto.phone());
        guardian.setAddress(dto.address());
        guardian.setOccupation(dto.occupation());

        return guardian;
    }

}