package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SubjectDto;
import com.SchoolManagementSystem.system.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.system.dto.user.TeacherDto;

import java.util.List;

public interface TeacherSubjectService {

    TeacherSubjectDto connectTeacherToSubject(Long teacherId, Long subjectId);

    List<SubjectDto> getSubjectByTeacherId(Long subjectId);

    List<TeacherDto> getTeacherBySubjectId(Long subjectId);
}