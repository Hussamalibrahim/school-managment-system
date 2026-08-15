package com.SchoolManagementSystem.system.controller.tenant.internal;

import com.SchoolManagementSystem.system.dto.internal.InternalStudentDto;
import com.SchoolManagementSystem.system.service.internal.InternalStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/students")
@RequiredArgsConstructor
public class InternalStudentController {

    private final InternalStudentService internalStudentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<InternalStudentDto> getStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                internalStudentService.getStudent(studentId)
        );
    }

    @GetMapping("/{studentId}/exists")
    public ResponseEntity<Boolean> exists(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                internalStudentService.exists(studentId)
        );
    }
}