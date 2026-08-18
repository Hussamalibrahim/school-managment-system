package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.config.FcmService;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.service.communication.AttendanceNotificationService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import com.SchoolManagementSystem.system.utils.NotificationTopicUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceNotificationServiceImpl implements AttendanceNotificationService {

    private final FcmService fcmService;

    @Override
    public void sendAbsenceNotification(Student student) {
        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            log.warn("Cannot send warning notification. School ID is null.");
            return;
        }
        String schoolCode = student.getSchool().getCode();

        String topic = NotificationTopicUtil.student(
                        schoolCode,
                        student.getId());

        //TODO deal with language inside server
        fcmService.sendToTopic(topic,
                "غياب الطالب",
                "تم تسجيل غياب الطالب " + student.getFirstName(),
                "ATTENDANCE_ABSENT");
    }
}
