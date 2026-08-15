package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface SchoolClassService extends CrudService<SchoolClassDto, Long> {
    SchoolClassDto save(SchoolClassDto dto);

    List<SchoolClassDto> getBySchoolClassByTeacher(Long refId);

}
