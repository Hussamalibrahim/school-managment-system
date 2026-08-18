package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.config.FcmService;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.service.communication.WarningNotificationService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import com.SchoolManagementSystem.system.utils.NotificationTopicUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarningNotificationServiceImpl implements WarningNotificationService {

    private final FcmService fcmService;

    @Override
    public void sendWarningNotification(Student student, String message) {

        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            log.warn("Cannot send warning notification. School ID is null.");
            return;
        }
        String schoolCode = student.getSchool().getCode();

        String topic = NotificationTopicUtil.student(
                        schoolCode,
                        student.getId());

        String body = message == null || message.isBlank()
                ? "تم تسجيل تحذير جديد للطالب "
                + student.getFirstName()
                + " "
                + student.getLastName()
                : message;

        fcmService.sendToTopic(topic, "تحذير جديد", body, "WARNING");
    }
}