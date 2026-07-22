package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface TeacherSubjectService {

    TeacherSubjectDto connectTeacherToSubject(Long teacherId, Long subjectId);

    List<SubjectDto> getSubjectByTeacherId(Long subjectId);

    List<TeacherDto> getTeacherBySubjectId(Long subjectId);
}