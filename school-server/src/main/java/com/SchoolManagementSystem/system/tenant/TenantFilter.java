package com.SchoolManagementSystem.system.tenant;


import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

        String path = request.getRequestURI();

        if(path.equals("/api/auth/register")) {
            chain.doFilter(request, response);
            return;
        }


            String schoolCode = tenantResolver.resolveSchoolCode(request);
            if(schoolCode != null){
                School school = schoolRepository.findByCode(schoolCode)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_URL));

                TenantContext.setSchoolId(school.getId());

                hibernateTenantFilter.enable();
                tenantHibernateFilter.enable();
            }
        try {
            chain.doFilter(
                    request,
                    response
            );
        } finally {
            hibernateTenantFilter.disable();
            tenantHibernateFilter.disable();
            TenantContext.clear();
        }
    }
}