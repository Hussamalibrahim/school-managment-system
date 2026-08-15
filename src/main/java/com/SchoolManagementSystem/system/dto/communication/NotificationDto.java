package com.SchoolManagementSystem.System.dto.communication;

import com.SchoolManagementSystem.System.entity.communication.Notification;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Notification}
 */
public record NotificationDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                              String title, String message, UserType userType, Long targetId, Boolean isRead,
                              LocalDate notificationDate) implements Serializable {
}