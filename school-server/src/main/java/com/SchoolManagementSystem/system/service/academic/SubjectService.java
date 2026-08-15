package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SubjectDto;
import com.SchoolManagementSystem.system.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface SubjectService extends CrudService<SubjectDto, Long> {
    SubjectDto save(SubjectCreateRequest dto);

    List<SubjectDto> getBySemester(SemesterName semesterName);

    List<SubjectDto> getByGrade(GradeLevel gradeLevel);

    List<SubjectDto> getSubjectByGradeAndSemester(GradeLevel gradeLevel, SemesterName semesterName);

}
