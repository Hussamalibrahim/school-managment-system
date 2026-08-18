package com.SchoolManagementSystem.system.dto.communication;

import com.SchoolManagementSystem.system.dto.communication.respones.NotificationTopicDto;

import java.util.List;

public record NotificationTopicsDto(List<NotificationTopicDto> topics) {
}