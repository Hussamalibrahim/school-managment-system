package com.SchoolManagementSystem.System.controller.tenant.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student-guardian")
public class StudentGuardianController {

    private final StudentGuardianService studentGuardianService;

    // connect student to guardian
    @PostMapping("/student/{studentId}/guardian/{guardianId}")
    public ResponseEntity<StudentGuardianDto> connect(
            @PathVariable Long studentId,
            @PathVariable Long guardianId,
            @RequestParam(defaultValue = "false") boolean primaryGuardian) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentGuardianService.connectStudentToGuardian(
                        studentId,
                        guardianId,
                        primaryGuardian)
                );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GuardianDto>> getStudentGuardians(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(studentGuardianService.getStudentGuardians(studentId));
    }

    @PutMapping("/student/{studentId}/primary-guardian/{guardianId}")
    public ResponseEntity<StudentGuardianDto> changePrimaryGuardian(
            @PathVariable Long studentId,
            @PathVariable Long guardianId) {

        return ResponseEntity.ok(studentGuardianService.changePrimaryGuardian(studentId, guardianId));
    }

    @GetMapping("/guardian/{guardianId}")
    public ResponseEntity<?> getGuardianStudents(
            @PathVariable Long guardianId,
            @AuthenticationPrincipal UserPrincipal user) {

        if (Role.GUARDIAN.equals(user.getRole())
                && !Objects.equals(user.getRefId(), guardianId)) {
            throw new ValidationException(ErrorCode.UNAUTHENTICATED);
        }

        return ResponseEntity.ok(
                studentGuardianService.getGuardianStudents(guardianId)
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

    @PutMapping("/student/{studentId}/guardian/{guardianId}")
    public ResponseEntity<Void> removeRelation(
            @PathVariable Long studentId,
            @PathVariable Long guardianId) {

        studentGuardianService.removeRelation(studentId, guardianId);

        return ResponseEntity.noContent().build();
    }
}