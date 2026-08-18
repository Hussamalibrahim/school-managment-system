package com.SchoolManagementSystem.system.service.communication;

import com.SchoolManagementSystem.system.entity.student.Student;

public interface AttendanceNotificationService {

    void sendAbsenceNotification(Student student);
}
