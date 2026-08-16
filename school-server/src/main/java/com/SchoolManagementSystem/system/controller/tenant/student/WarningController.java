package com.SchoolManagementSystem.system.controller.tenant.student;

import com.SchoolManagementSystem.system.dto.student.respones.GuardianStudentWarningsDto;
import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.dto.student.request.CreateWarningDto;
import com.SchoolManagementSystem.system.dto.student.respones.WarningStatisticsDto;
import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.student.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warnings")
public class WarningController{

    private final WarningService warningService;

    @GetMapping("/my-children")
    public ResponseEntity<List<GuardianStudentWarningsDto>> getMyChildrenWarnings(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(warningService.getGuardianChildrenWarnings(user.getRefId()));
    }

    @PostMapping
    public ResponseEntity<WarningDto> create(
            @RequestBody CreateWarningDto dto,
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(warningService.create(dto, user.getRefId()));
    }
    @GetMapping("/statistics")
    public ResponseEntity<List<WarningStatisticsDto>> getWarningStatistics(
            @RequestParam(required = false) WarningReason reason,
            @RequestParam(required = false) Long count) {

        return ResponseEntity.ok(warningService.getWarningStatistics(reason, count));
    }
    @GetMapping("/{id}")
    public ResponseEntity<WarningDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(warningService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<WarningDto>> getAll() {
        return ResponseEntity.ok(warningService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        warningService.delete(id);
        return ResponseEntity.noContent().build();
    }

}