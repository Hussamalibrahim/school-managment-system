package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.dto.academic.ExamDto;
import com.SchoolManagementSystem.system.dto.academic.request.ExamCreateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.ExamUpdateRequest;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<ExamDto> create(
            @Valid @RequestBody ExamCreateRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(examService.save(request, user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<ExamDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ExamUpdateRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(examService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        examService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<ExamDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<ExamDto>> getAll() {
        return ResponseEntity.ok(examService.getAll());
    }

    @GetMapping("/class")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','STUDENT','GUARDIAN')")
    public ResponseEntity<List<ExamDto>> getByClass(
            @RequestParam Long classId,
            @RequestParam SemesterName semesterName
    ) {
        return ResponseEntity.ok(
                examService.getByClass(classId, semesterName)
        );
    }

    @GetMapping("/subject")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<ExamDto>> getBySubject(
            @RequestParam Long subjectId,
            @RequestParam SemesterName semesterName
    ) {
        return ResponseEntity.ok(
                examService.getBySubject(subjectId, semesterName)
        );
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<ExamDto>> getTeacherExams(
            @RequestParam Long classId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(
                examService.getTeacherExams(classId, user)
        );
    }
    @GetMapping("/my-class")
    @PreAuthorize("hasAnyRole('STUDENT','GUARDIAN')")
    public ResponseEntity<List<ExamDto>> getMyClassExams(
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(examService.getMyClassExams(user));
    }
}