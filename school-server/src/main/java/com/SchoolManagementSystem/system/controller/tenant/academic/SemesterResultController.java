package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterResultDto;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.SemesterResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semester-results")
@RequiredArgsConstructor
public class SemesterResultController {

    private final SemesterResultService semesterResultService;


    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<List<SemesterResultDto>> getByStudent(
            @PathVariable Long studentId,
            @RequestParam Long semesterId) {

        return ResponseEntity.ok(semesterResultService.getByStudent(studentId, semesterId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY','TEACHER')")
    public ResponseEntity<SemesterResultDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(semesterResultService.getById(id));
    }

    @PostMapping("/finalize/{semesterName}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<Void> finalizeSemester(@PathVariable SemesterName semesterName) {

        semesterResultService.finalizeSemester(semesterName);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<SemesterResultDto>> getMyResults(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam Long semesterId) {

        return ResponseEntity.ok(semesterResultService
                        .getByStudent(user.getRefId(), semesterId));
    }
    @GetMapping("/my-children")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<List<SemesterResultDto>> getMyChildrenResults(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam Long semesterId) {

        return ResponseEntity.ok(semesterResultService
                .getGuardianChildrenResults(user.getRefId(), semesterId));
    }
}