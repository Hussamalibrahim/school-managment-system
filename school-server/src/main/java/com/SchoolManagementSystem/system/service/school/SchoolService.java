package com.SchoolManagementSystem.system.service.school;

import com.SchoolManagementSystem.system.dto.request.DefineSchool;
import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.service.CrudService;

public interface SchoolService extends CrudService<SchoolDto, Long> {
    void defineSchool(DefineSchool defineSchool);

    SchoolDto update(Long aLong, updateSchoolInfo dto);

    void register(SchoolRegisterRequest request);

    School findByCode(String code);
}
