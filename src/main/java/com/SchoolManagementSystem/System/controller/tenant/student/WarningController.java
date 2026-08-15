package com.SchoolManagementSystem.System.controller.tenant.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import com.SchoolManagementSystem.System.service.student.WarningService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warnings")
public class WarningController extends BaseCrudController<WarningDto> {

    private final WarningService warningService;
    private final StudentGuardianService studentGuardianService;

    public WarningController(WarningService service, StudentGuardianService studentGuardianService) {
        super(service);
        this.warningService = service;
        this.studentGuardianService = studentGuardianService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<WarningDto>> getMyWarnings(
            @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(warningService.getWarningsByStudentId(user.getRefId()));
    }

    @GetMapping("/guardian/student/{studentId}")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<List<WarningDto>> getStudentWarningsForGuardian(
            @PathVariable Long studentId,
            @AuthenticationPrincipal UserPrincipal user) {
        if (!studentGuardianService.isStudentBelongsToGuardian(studentId, user.getRefId())) {
            throw new ValidationException(ErrorCode.UNAUTHENTICATED);
        }
        return ResponseEntity.ok(warningService.getWarningsByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<List<WarningDto>> getStudentWarnings(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(warningService.getWarningsByStudentId(studentId));
    }
}