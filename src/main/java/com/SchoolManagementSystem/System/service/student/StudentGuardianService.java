package com.SchoolManagementSystem.System.service.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface StudentGuardianService extends CrudService<StudentGuardianDto, Long> {
    StudentGuardianDto connectStudentToGuardian(Long studentId, Long guardianId,Boolean primaryGuardian);

    List<GuardianDto> getGuardiansByStudent(Long studentId);

    List<StudentDto> getStudentsByGuardian(Long guardianId);
}
