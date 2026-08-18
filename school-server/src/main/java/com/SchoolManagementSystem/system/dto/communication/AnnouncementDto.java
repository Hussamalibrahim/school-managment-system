package com.SchoolManagementSystem.system.dto.communication;

import com.SchoolManagementSystem.system.entity.enumeration.AnnouncementTargetType;
import com.SchoolManagementSystem.system.entity.enumeration.Role;

import java.time.LocalDateTime;

public record AnnouncementDto(
        Long id,
        String title,
        String content,
        AnnouncementTargetType targetType,
        Role targetRole,
        Long targetId,
        LocalDateTime publishedAt,
        Boolean active) {
}