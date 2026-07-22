package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.dto.user.SecretaryDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.security.dto.AuthRequestGuardian;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import com.SchoolManagementSystem.System.service.student.StudentService;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import com.SchoolManagementSystem.System.service.user.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secretary")
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