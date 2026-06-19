package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.academic.TeacherSubject;
import com.SchoolManagementSystem.System.entity.user.Teacher;

public final class TeacherSubjectMapper
{
    private TeacherSubjectMapper()
    {
    }

    public static TeacherSubjectDto toDto(
            TeacherSubject teacherSubject)
    {
        if (teacherSubject == null)
            return null;

        return new TeacherSubjectDto(
                teacherSubject.getId(),

                teacherSubject.getTeacher() != null
                        ? teacherSubject.getTeacher().getId()
                        : null,

                teacherSubject.getSubject() != null
                        ? teacherSubject.getSubject().getId()
                        : null
        );
    }

    public static TeacherSubject toEntity(
            TeacherSubjectDto dto)
    {
        if (dto == null)
            return null;

        TeacherSubject teacherSubject =
                new TeacherSubject();

        teacherSubject.setId(dto.id());

        if (dto.teacherId() != null)
        {
            Teacher teacher = new Teacher();
            teacher.setId(dto.teacherId());

            teacherSubject.setTeacher(teacher);
        }

        if (dto.subjectId() != null)
        {
            Subject subject = new Subject();
            subject.setId(dto.subjectId());

            teacherSubject.setSubject(subject);
        }

        return teacherSubject;
    }
}