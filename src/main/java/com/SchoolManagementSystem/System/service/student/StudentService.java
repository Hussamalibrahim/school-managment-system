package com.SchoolManagementSystem.System.service.student;


import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface StudentService extends CrudService<StudentDto, Long>
{
     StudentDto assignClass(Long studentId, Long classId);

}
