package com.SchoolManagementSystem.system.security;

import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private AuthUser authUser;

    private final Long refId;
    private final String role;
    private final Long schoolId;

    public UserPrincipal(Long refId, String role, Long schoolId) {
        this.refId = refId;
        this.role = role;
        this.schoolId = schoolId;
    }

    public UserPrincipal(AuthUser authUser) {
        this.authUser = authUser;
        this.refId = authUser.getRefId();
        this.role = authUser.getRole().name();
        this.schoolId = authUser.getSchool() != null ? authUser.getSchool().getId() : null;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole().name()));
    }


    @Override
    public String getPassword() {
        if (authUser == null) {
            return null;
        }
        return authUser.getPassword();
    }


    @Override
    public String getUsername() {

        if (authUser == null) {
            return String.valueOf(refId);
        }

        return authUser.getEmail();
    }


    public Long getSchoolId() {

        if (authUser != null) {
            return authUser.getSchool() != null ? authUser.getSchool().getId() : null;
        }

        return schoolId;
    }


    public String getSchoolCode() {

        if (authUser == null) {
            return null;
        }
        return authUser.getSchool() != null ? authUser.getSchool().getCode() : null;
    }


    public Long getRefId() {

        if (authUser != null) {
            return authUser.getRefId();
        }

        return refId;
    }


    public Role getRole() {

        if (authUser != null) {
            return authUser.getRole();
        }

        return Role.valueOf(role);
    }


    public boolean hasRole(String role) {

        return getRole()
                .name()
                .equalsIgnoreCase(role);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        if (authUser == null) {
            return true;
        }
        return Boolean.TRUE.equals(authUser.getEnabled());
    }
}