package com.SchoolManagementSystem.System.dto.academic;

import java.io.Serializable;

public record TeacherSubjectDto(

        Long id,

        Long teacherId,

        Long subjectId

) implements Serializable {
}