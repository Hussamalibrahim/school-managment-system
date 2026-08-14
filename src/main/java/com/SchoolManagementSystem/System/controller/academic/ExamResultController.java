package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.System.dto.academic.request.SaveExamResultsRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.ExamResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;

@RestController
@RequestMapping("/api/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;
    private final StudentGuardianService studentGuardianService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<ExamResultDto>> saveResults(
            @Valid @RequestBody SaveExamResultsRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(
                examResultService.saveResults(request, user)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<ExamResultDto> getById(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long id) {
        return ResponseEntity.ok(
                examResultService.getById(user, id)
        );
    }

    @GetMapping("/exam")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<List<ExamResultDto>> getByExam(
            @RequestParam Long examId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(
                examResultService.getByExam(examId, user)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','TEACHER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        examResultService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<ExamResultDto>> getAll() {
        return ResponseEntity.ok(
                examResultService.getAll()
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ExamResultDto>> getMyResults(
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(
                examResultService.getStudentResults(user.getRefId())
        );
    }

    @GetMapping("/guardian/student/{studentId}")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<List<ExamResultDto>> getStudentResultsForGuardian(
            @PathVariable Long studentId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        if (!studentGuardianService.isStudentBelongsToGuardian(studentId, user.getRefId())) {
            throw new ValidationException(ErrorCode.UNAUTHENTICATED);
        }
        return ResponseEntity.ok(
                examResultService.getStudentResults(studentId)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<List<ExamResultDto>> getStudentResults(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                examResultService.getStudentResults(studentId)
        );
    }
}