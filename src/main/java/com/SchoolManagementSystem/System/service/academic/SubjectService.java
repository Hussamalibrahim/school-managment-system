package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface SubjectService extends CrudService<SubjectDto, Long> {
    SubjectDto save(SubjectCreateRequest dto);

    List<SubjectDto> getBySemester(Semester semester);

    List<SubjectDto> getByGrade(GradeLevel gradeLevel);

    List<SubjectDto> getSubjectByGradeAndSemester(GradeLevel gradeLevel, Semester semester);

}
