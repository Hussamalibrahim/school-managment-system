package com.SchoolManagementSystem.system.dto.communication.respones;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificationTopicDto(
        String topic,
        Long id,
        String title,
        String content,
        LocalDateTime publishedAt
) {
}