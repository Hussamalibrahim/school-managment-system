package com.SchoolManagementSystem.system.service.school;

import com.SchoolManagementSystem.system.dto.request.DefineSchool;
import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.Set;

public interface SchoolService extends CrudService<SchoolDto, Long> {

    SchoolDto update(Long aLong, updateSchoolInfo dto);

    void register(SchoolRegisterRequest request);

    SchoolDto findByUrl();

    Set<GradeLevel> availableGrades();

}
