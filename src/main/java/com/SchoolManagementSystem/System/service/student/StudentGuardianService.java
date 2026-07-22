package com.SchoolManagementSystem.System.service.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface StudentGuardianService extends CrudService<StudentGuardianDto, Long> {
    StudentGuardianDto connectStudentToGuardian(Long studentId, Long guardianId, Boolean primaryGuardian);

    List<GuardianDto> getStudentGuardians(Long studentId);

    List<StudentDto> getGuardianStudents(Long guardianId);

    List<StudentDto> getStudentsWithoutGuardian();

    List<GuardianDto> getGuardiansWithoutStudents();

    StudentGuardianDto changePrimaryGuardian(Long studentId, Long newGuardianId);

    void removeGuardian(Long studentId, Long guardianId);

    boolean isStudentBelongsToGuardian(Long studentId, Long guardianId);

}
