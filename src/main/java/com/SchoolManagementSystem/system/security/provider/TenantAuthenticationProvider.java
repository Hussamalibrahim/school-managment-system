package com.SchoolManagementSystem.System.security.provider;


import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.security.auth.TenantAuthenticationToken;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TenantAuthenticationProvider implements AuthenticationProvider {


    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
            Authentication authentication) {

        TenantAuthenticationToken token = (TenantAuthenticationToken) authentication;
        String email = token.getName();
        String password = token.getCredentials().toString();
        String schoolCode = token.getSchoolCode();

        UserDetails user = userDetailsService.loadUserByUsernameAndSchool(email, schoolCode);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ValidationException(ErrorCode.INVALID_PASSWORD);
        }



        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
    }



    @Override
    public boolean supports(
            Class<?> authentication
    ) {

        return TenantAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}