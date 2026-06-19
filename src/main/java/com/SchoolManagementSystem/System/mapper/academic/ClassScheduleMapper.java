package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.user.Teacher;

public final class ClassScheduleMapper {

    private ClassScheduleMapper() {}

    public static ClassScheduleDto toDto(ClassSchedule classSchedule) {
        if (classSchedule == null) return null;

        return new ClassScheduleDto(
                classSchedule.getId(),
                classSchedule.getCreatedAt(),
                classSchedule.getUpdatedAt(),
                classSchedule.getDeletedAt(),

                classSchedule.getSchoolClass() != null ? classSchedule.getSchoolClass().getId() : null,
                classSchedule.getSubject() != null ? classSchedule.getSubject().getId() : null,
                classSchedule.getTeacher() != null ? classSchedule.getTeacher().getId() : null,

                classSchedule.getDayOfWeek(),
                classSchedule.getPeriodNumber()
        );
    }

    public static ClassSchedule toEntity(ClassScheduleDto dto) {
        if (dto == null) return null;

        ClassSchedule classSchedule = new ClassSchedule();

        classSchedule.setId(dto.id());
        classSchedule.setCreatedAt(dto.createdAt());
        classSchedule.setUpdatedAt(dto.updatedAt());
        classSchedule.setDeletedAt(dto.deletedAt());

        if (dto.schoolClassId() != null) {
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(dto.schoolClassId());
            classSchedule.setSchoolClass(schoolClass);
        }

        if (dto.subjectId() != null) {
            Subject subject = new Subject();
            subject.setId(dto.subjectId());
            classSchedule.setSubject(subject);
        }

        if (dto.teacherId() != null) {
            Teacher teacher = new Teacher();
            teacher.setId(dto.teacherId());
            classSchedule.setTeacher(teacher);
        }

        classSchedule.setDayOfWeek(dto.dayOfWeek());
        classSchedule.setPeriodNumber(dto.periodNumber());

        return classSchedule;
    }
}