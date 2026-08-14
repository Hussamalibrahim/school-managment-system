package com.SchoolManagementSystem.System.security;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final AuthUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Long getSchoolId(){
        return user.getSchool() != null
                ? user.getSchool().getId()
                : null;
    }

    public String getSchoolCode(){
        return user.getSchool() != null
                ? user.getSchool().getCode()
                : null;
    }
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public Long getRefId() {
        return user.getRefId();
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
        return Boolean.TRUE.equals(user.getEnabled());
    }

    public Role getRole() {
        return user.getRole();
    }



    public boolean hasRole(String role) {
        return user.getRole().name().equalsIgnoreCase(role);
    }
}