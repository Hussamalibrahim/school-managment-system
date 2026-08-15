package com.SchoolManagementSystem.System.service.student;


import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface StudentService extends CrudService<StudentDto, Long> {
     StudentDto assignClass(Long studentId, Long classId);
     void save(AuthRequestStudent authRequestStudent);
     List<SubjectNameDto> getNamesSubjectByGradeAndSemester(long id);
     List<StudentDto> getStudentsByClass_Id(Long id);

    List<StudentDto> getStudentsTeacherByClassId(Long classId, Long refId);
}
