package com.SchoolManagementSystem.System.controller.tenant.school;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school-legacy")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/define-school")
    public ResponseEntity<Void> defineSchool(@RequestBody DefineSchool defineSchool) {
        schoolService.defineSchool(defineSchool);
        return ResponseEntity.noContent().build();
    }
}