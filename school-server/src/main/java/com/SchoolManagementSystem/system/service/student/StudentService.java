package com.SchoolManagementSystem.system.service.student;


import com.SchoolManagementSystem.system.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface StudentService extends CrudService<StudentDto, Long> {
     StudentDto assignClass(Long studentId, Long classId);
     void save(AuthRequestStudent authRequestStudent);
     List<SubjectNameDto> getNamesSubjectByGradeAndSemester(long id);
     List<StudentDto> getStudentsByClass_Id(Long id);

    List<StudentDto> getStudentsTeacherByClassId(Long classId, Long refId);

}
