package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.service.CrudService;

public interface TeacherSubjectService extends CrudService<TeacherSubjectDto, Long> {

    TeacherSubjectDto connectTeacherToSubject(Long teacherId, Long subjectId);
}