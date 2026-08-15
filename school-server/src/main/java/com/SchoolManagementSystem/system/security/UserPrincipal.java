package com.SchoolManagementSystem.system.security;

import com.SchoolManagementSystem.system.entity.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private AuthUser authUser;
    private Long refId;
    private String role;
    private Long schoolId;
    public UserPrincipal(
            Long refId,
            String role,
            Long schoolId){

        this.refId = refId;
        this.role = role;
        this.schoolId = schoolId;

    } public UserPrincipal(
            AuthUser authUser){
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    public Long getSchoolId(){
        return authUser.getSchool() != null
                ? authUser.getSchool().getId()
                : null;
    }

    public String getSchoolCode(){
        return authUser.getSchool() != null
                ? authUser.getSchool().getCode()
                : null;
    }
    @Override
    public String getUsername() {
        return authUser.getEmail();
    }

    public Long getRefId() {
        return authUser.getRefId();
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
        return Boolean.TRUE.equals(authUser.getEnabled());
    }

    public Role getRole() {
        return authUser.getRole();
    }



    public boolean hasRole(String role) {
        return authUser.getRole().name().equalsIgnoreCase(role);
    }
}