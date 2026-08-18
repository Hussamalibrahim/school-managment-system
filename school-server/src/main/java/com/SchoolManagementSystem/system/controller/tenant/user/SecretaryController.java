package com.SchoolManagementSystem.system.controller.tenant.user;

import com.SchoolManagementSystem.system.dto.student.request.AuthRequestStudent;
import com.SchoolManagementSystem.system.dto.user.SecretaryDto;
import com.SchoolManagementSystem.system.dto.user.request.AuthRequestGuardian;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.student.StudentGuardianService;
import com.SchoolManagementSystem.system.service.student.StudentService;
import com.SchoolManagementSystem.system.service.user.GuardianService;
import com.SchoolManagementSystem.system.service.user.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secretary")
@RequiredArgsConstructor
public class SecretaryController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final SecretaryService secretaryService;
    private final StudentGuardianService studentGuardianService;

    @PostMapping("/student")
    public ResponseEntity<Void> createStudent(
            @RequestBody AuthRequestStudent dto) {

        studentService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/guardian")
    public ResponseEntity<Void> createGuardian(
            @RequestBody AuthRequestGuardian dto) {

        guardianService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<SecretaryDto> me(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(secretaryService.getById(user.getRefId()));
    }
}