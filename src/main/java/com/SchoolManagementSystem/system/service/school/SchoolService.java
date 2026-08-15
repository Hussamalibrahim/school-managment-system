package com.SchoolManagementSystem.System.service.school;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.service.CrudService;

public interface SchoolService extends CrudService<SchoolDto, Long> {
    void defineSchool(DefineSchool defineSchool);

    SchoolDto update(Long aLong, updateSchoolInfo dto);

    void register(SchoolRegisterRequest request);

    School findByCode(String code);
}
