package com.SchoolManagementSystem.System.dto.communication;

import com.SchoolManagementSystem.System.entity.communication.Announcement;
import com.SchoolManagementSystem.System.entity.enumeration.AnnouncementStatus;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Announcement}
 */
public record AnnouncementDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        String title,
        String content,

        UserType userType,

        Long targetId,

        LocalDate publishDate,

        AnnouncementStatus status
) implements Serializable {
}