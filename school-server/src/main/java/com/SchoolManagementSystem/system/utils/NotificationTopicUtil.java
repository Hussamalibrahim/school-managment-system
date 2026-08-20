package com.SchoolManagementSystem.system.utils;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.Role;


public final class NotificationTopicUtil {

    private NotificationTopicUtil() {
    }

    public static String all(String schoolCode) {

        return normalize(schoolCode) + "_ALL";
    }

    public static String role(String schoolCode, Role role) {

        return normalize(schoolCode) + "_" + role.name();
    }

    public static String gradeLevel(String schoolCode, GradeLevel gradeId) {

        return normalize(schoolCode) + "_GRADE_" + gradeId.name();
    }

    public static String schoolClass(String schoolCode, Long classId) {

        return normalize(schoolCode) + "_CLASS_" + classId;
    }

    public static String student(String schoolCode, Long studentId) {

        return normalize(schoolCode) + "_STUDENT_" + studentId;
    }

    public static String user(String schoolCode, Long userId) {

        return normalize(schoolCode) + "_USER_" + userId;
    }

    private static String normalize(String schoolCode) {

        return schoolCode
                .trim()
                .toLowerCase()
                .replaceAll(
                        "[^a-z0-9_-]",
                        "_");
    }
}
