package com.SchoolManagementSystem.system.service.communication;

import com.SchoolManagementSystem.system.entity.student.Student;

public interface WarningNotificationService {
    void sendWarningNotification(Student student, String message);

}
