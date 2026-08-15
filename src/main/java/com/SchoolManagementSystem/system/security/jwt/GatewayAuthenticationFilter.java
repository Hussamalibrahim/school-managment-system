package com.SchoolManagementSystem.system.security.jwt;


import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.exception.security.JwtAuthenticationException;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.security.service.JwtService;
import com.SchoolManagementSystem.system.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsService;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token =
                header.substring(7);
        try {
            String email = jwtService.extractEmail(token);
            Long tokenSchoolId =
                    jwtService.extractSchoolId(token);

            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {
                UserDetails user = userDetailsService.loadUserByUsernameAndSchool(email, tokenSchoolId);

                UserPrincipal principal = (UserPrincipal) user;


                if (principal.getSchoolId() != null && !Objects.equals(principal.getSchoolId(), tokenSchoolId)) {
                    throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN);
                }
                if(jwtService.isValid(token,user)){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                }
            }
        } catch (JwtException e) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN);
        }
        chain.doFilter(request,response);
    }
}