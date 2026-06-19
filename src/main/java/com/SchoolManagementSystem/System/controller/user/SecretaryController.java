package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.dto.user.SecretaryDto;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import com.SchoolManagementSystem.System.service.student.StudentService;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import com.SchoolManagementSystem.System.service.user.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secretary")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SECRETARY')")
public class SecretaryController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final StudentGuardianService studentGuardianService;

    // add student
    @PostMapping("/student")
    public StudentDto createStudent(@RequestBody StudentDto dto) {
        return studentService.save(dto);
    }

    // Add guardian
    @PostMapping("/guardian")
    public GuardianDto createGuardian(@RequestBody GuardianDto dto) {
        return guardianService.save(dto);
    }

    // connect student to guardian
    @PostMapping("/connect")
    public StudentGuardianDto connect(
            @RequestParam Long studentId,
            @RequestParam Long guardianId,
            @RequestParam(defaultValue = "false") boolean primary
    ) {
        return studentGuardianService.connectStudentToGuardian(studentId, guardianId, primary);
    }
}