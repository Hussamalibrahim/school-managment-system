package com.SchoolManagementSystem.System.security.mapper;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;

public final class AuthUserMapper {
    private AuthUserMapper() {
    }

    public static AuthUserDto toDto(AuthUser authUser) {
        return new AuthUserDto(
                authUser.getEmail(),
                authUser.getPassword(),
                authUser.getRole(),
                authUser.getRefId(),
                authUser.getSchool() != null ? authUser.getSchool().getId() : null,
                authUser.getSchool() != null ? authUser.getSchool().getCode() : null
        );
    }

    public static AuthUser toEntity(AuthUserDto authUserDto) {
        if (authUserDto == null) {
            return null;
        }
        AuthUser authUser = new AuthUser();
        authUser.setEmail(authUserDto.email());
        authUser.setPassword(authUserDto.password());
        authUser.setRole(authUserDto.role());
        authUser.setRefId(authUserDto.refId());

        return authUser;
    }
    public static AuthUser fromRegisterRequest(
            String email,
            String encodedPassword,
            Long refId,
            Role role) {

        AuthUser authUser = new AuthUser();

        authUser.setEmail(email);
        authUser.setPassword(encodedPassword);
        authUser.setRole(role);
        authUser.setRefId(refId);

        return authUser;
    }
    public static AuthUser fromRegisterRequest(
            String email,
            String encodedPassword,
            Long refId,
            Role role,
            School school) {

        AuthUser authUser = new AuthUser();

        authUser.setEmail(email);
        authUser.setPassword(encodedPassword);
        authUser.setRole(role);
        authUser.setRefId(refId);
        authUser.setSchool(school);

        return authUser;
    }
}
