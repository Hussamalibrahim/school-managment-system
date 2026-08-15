package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface SchoolClassService extends CrudService<SchoolClassDto, Long> {
    SchoolClassDto save(SchoolClassDto dto);

    List<SchoolClassDto> getBySchoolClassByTeacher(Long refId);

}
