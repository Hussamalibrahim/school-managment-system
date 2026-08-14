package com.SchoolManagementSystem.System.controller.tenant.academic;

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

@RestController
@RequestMapping("/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

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
}