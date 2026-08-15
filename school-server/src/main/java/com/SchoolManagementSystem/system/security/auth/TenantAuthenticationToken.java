package com.SchoolManagementSystem.system.security.auth;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Getter
public class TenantAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String schoolCode;

    public TenantAuthenticationToken(
            String email,
            String password,
            String schoolCode) {
        super(email, password);

        this.schoolCode = schoolCode;
    }
}