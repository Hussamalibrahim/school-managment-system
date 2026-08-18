package com.SchoolManagementSystem.system.service.communication;

import com.SchoolManagementSystem.system.dto.communication.NotificationTopicsDto;
import com.SchoolManagementSystem.system.dto.communication.respones.NotificationTopicDto;
import com.SchoolManagementSystem.system.security.UserPrincipal;

public interface NotificationTopicService {

    NotificationTopicsDto getTopics(UserPrincipal userPrincipal);
}