package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.config.FcmService;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import com.SchoolManagementSystem.system.utils.NotificationTopicUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.SchoolManagementSystem.system.entity.enumeration.UserType.ALL;

@Service
@RequiredArgsConstructor
public class AnnouncementNotificationService {

    private final FcmService fcmService;

    public void send(Announcement announcement, AnnouncementTarget target, String schoolCode) {

        String topic = resolveTopic(target, schoolCode);

        fcmService.sendAnnouncementNotification(
                topic,
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getId());
    }

    private String resolveTopic(
            AnnouncementTarget target,
            String schoolCode) {

        return switch (target.getType()) {

            case ALL -> NotificationTopicUtil.all(schoolCode);

            case ROLE -> NotificationTopicUtil.role(schoolCode, target.getTargetRole());

            case GRADE_LEVEL -> NotificationTopicUtil.gradeLevel(schoolCode, target.getTargetGradeLevel());

            case SCHOOL_CLASS -> NotificationTopicUtil.schoolClass(schoolCode, target.getTargetId());

            case STUDENT -> NotificationTopicUtil.student(schoolCode, target.getTargetId());

            case USER -> NotificationTopicUtil.user(schoolCode, target.getTargetId());
        };
    }
}