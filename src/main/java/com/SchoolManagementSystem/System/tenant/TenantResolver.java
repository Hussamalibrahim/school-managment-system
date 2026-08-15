package com.SchoolManagementSystem.System.tenant;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public final class TenantResolver {

    private static final Set<String> SYSTEM_PREFIXES = Set.of(
            "auth", "schools", "school", "files", "error", "actuator",
            "principal", "secretary", "teacher", "students", "guardians",
            "attendance", "assessments", "assessment-results", "exams", "exam-results",
            "warnings", "payments", "class-fees", "fee-types", "discounts", "student-discounts",
            "book", "borrowed-book", "library", "announcements", "notifications", "schedules", "classes", "subjects"
    );

    public String resolveSchoolCode(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) return null;

        String[] parts = path.split("/");

        // URL format: /api/{schoolCode}/...
        if (parts.length >= 3 && "api".equals(parts[1])) {
            String candidate = parts[2].toLowerCase();
            if (!SYSTEM_PREFIXES.contains(candidate)) {
                return parts[2];
            }
        }
        return null;
    }
}