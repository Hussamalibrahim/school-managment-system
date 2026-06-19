package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.request.CreateClassRequest;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface SchoolClassService extends CrudService<SchoolClassDto, Long> {
    List<StudentDto> getStudentsById(Long id);
    SchoolClassDto save(SchoolClassDto dto);
}
