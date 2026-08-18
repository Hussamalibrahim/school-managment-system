package com.SchoolManagementSystem.system.dto.communication.request;

import com.SchoolManagementSystem.system.entity.enumeration.AnnouncementTargetType;
import com.SchoolManagementSystem.system.entity.enumeration.Role;

import java.time.LocalDateTime;

public record AnnouncementRequest(
        String title,
        String content,
        AnnouncementTargetType targetType,
        Role targetRole,
        Long targetId) {
}