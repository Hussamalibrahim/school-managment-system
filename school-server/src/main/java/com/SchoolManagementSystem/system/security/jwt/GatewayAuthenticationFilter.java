package com.SchoolManagementSystem.system.security.jwt;

import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.security.service.UserDetailsServiceImpl;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class GatewayAuthenticationFilter extends OncePerRequestFilter {

    @Value("${gateway.secret}")
    private String gatewayKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String gateway = request.getHeader("X-GATEWAY");
        logger.info("Gateway header value: {}"+ gateway);
        logger.info("Gateway header value: {}"+ gatewayKey);
        if(!gatewayKey.equals(gateway)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userId = request.getHeader("X-USER-ID");
        String role = request.getHeader("X-ROLE");
        String schoolId = request.getHeader("X-SCHOOL-ID");

        if(userId != null && role != null){
            TenantContext.setSchoolId(Long.parseLong(schoolId));
            UserPrincipal principal = new UserPrincipal(Long.parseLong(userId), role, Long.parseLong(schoolId));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role)));

            SecurityContextHolder.getContext().setAuthentication(authentication);}
        try {

            chain.doFilter(request,response);

        }
        finally {

            TenantContext.clear();
            SecurityContextHolder.clearContext();

        }
    }
}