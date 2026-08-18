package com.SchoolManagementSystem.system.utils;

import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;

import java.time.LocalDate;

public final class SemesterValidDate {

    public static void validateSemesterDates(LocalDate firstStart, LocalDate firstEnd, LocalDate secondStart, LocalDate secondEnd) {

        if (firstStart == null || firstEnd == null || secondStart == null || secondEnd == null) {

            throw new ValidationException(ErrorCode.INVALID_SEMESTER_DATES);
        }
        if (!firstStart.isBefore(firstEnd) || !secondStart.isBefore(secondEnd)) {
            throw new ValidationException(ErrorCode.INVALID_SEMESTER_DATES);
        }
        if (!secondStart.isAfter(firstEnd)) {
            throw new ValidationException(ErrorCode.SEMESTERS_OVERLAP);
        }
    }
    public static void validateSemesterDates(LocalDate firstStart, LocalDate firstEnd) {

        if (firstStart == null || firstEnd == null) {
            throw new ValidationException(ErrorCode.INVALID_SEMESTER_DATES);
        }
        if (!firstStart.isBefore(firstEnd)) {
            throw new ValidationException(ErrorCode.INVALID_SEMESTER_DATES);
        }

    }
}
