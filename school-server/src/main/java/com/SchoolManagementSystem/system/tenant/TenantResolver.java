package com.SchoolManagementSystem.system.tenant;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public final class TenantResolver {


    public String resolveSchoolCode(
            HttpServletRequest request) {

        String path = request.getRequestURI();

        String[] parts = path.split("/");

        if(parts.length >= 3 && "api".equals(parts[1])) {
            return parts[2];
        }
        return null;
    }
}