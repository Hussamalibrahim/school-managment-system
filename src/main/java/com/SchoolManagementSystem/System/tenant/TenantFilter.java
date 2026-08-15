package com.SchoolManagementSystem.System.tenant;

import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final HibernateTenantFilter hibernateTenantFilter;
    private final TenantResolver tenantResolver;
    private final SchoolRepository schoolRepository;
    private final TenantHibernateFilter tenantHibernateFilter;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain) throws ServletException, IOException {

        String schoolCode = tenantResolver.resolveSchoolCode(request);
        boolean filterEnabled = false;

        if (schoolCode != null) {
            School school = schoolRepository.findByCode(schoolCode).orElse(null);
            if (school != null) {
                TenantContext.set(school.getId(), school.getCode());
                hibernateTenantFilter.enable();
                tenantHibernateFilter.enable();
                filterEnabled = true;
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            if (filterEnabled) {
                hibernateTenantFilter.disable();
                tenantHibernateFilter.disable();
                TenantContext.clear();
            }
        }
    }
}