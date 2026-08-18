package com.SchoolManagementSystem.system.security.jwt;

import com.SchoolManagementSystem.system.entity.enumeration.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AdminGatewayAuthenticationFilter
        extends OncePerRequestFilter {

    @Value("${gateway.secret}")
    private String gatewayKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String admin = request.getHeader("X-ADMIN");

        if (!"true".equals(admin)) {
            chain.doFilter(request, response);
            return;
        }

        String gateway = request.getHeader("X-GATEWAY");
        if (!gatewayKey.equals(gateway)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String role = request.getHeader("X-ROLE");

        if (!Role.SUPER_ADMIN.name().equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "SUPER_ADMIN",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        try {
            chain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}