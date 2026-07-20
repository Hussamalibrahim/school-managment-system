package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student-guardian")
public class StudentGuardianController {

    private final StudentGuardianService studentGuardianService;
    private final GuardianService guardianService;


    // connect student to guardian
    @PostMapping("/connect")
    public StudentGuardianDto connect(
            @RequestParam Long studentId,
            @RequestParam Long guardianId,
            @RequestParam(defaultValue = "false") boolean primary
    ) {
        return studentGuardianService.connectStudentToGuardian(studentId, guardianId, primary);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getGuardiansByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                studentGuardianService
                        .getGuardiansByStudent(studentId)
        );
    }

    @GetMapping("/guardian/{guardianId}")
    public ResponseEntity<?> getStudentsByGuardian(
            @PathVariable Long guardianId, Authentication auth) {

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        if (Role.GUARDIAN.equals(user.getRole())) {
            if (!Objects.equals(guardianService.getById(user.getRefId()).id(), guardianId)) {
                return ResponseEntity.status(403).body("You are not authorized to view this information.");
            }
        }

        return ResponseEntity.ok(
                studentGuardianService
                        .getStudentsByGuardian(guardianId)
        );
    }
    @GetMapping("/guardians-without-students")
    public ResponseEntity<List<GuardianDto>> getGuardiansWithoutStudents() {
        return ResponseEntity.ok(
                studentGuardianService
                        .getGuardiansWithoutStudents()
        );
    }
    @GetMapping("/students-without-guardians")
    public ResponseEntity<List<StudentDto>> getStudentsWithoutGuardian() {
        return ResponseEntity.ok(
                studentGuardianService
                        .getStudentsWithoutGuardian()
        );
    }

}