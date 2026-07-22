package com.SchoolManagementSystem.System.controller.school;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class SchoolController{

    private final SchoolService schoolService;

    @RequestMapping("define-school")
    public ResponseEntity<Void> defineSchool(@RequestBody DefineSchool defineSchool) {
        schoolService.defineSchool(defineSchool);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> update(@PathVariable Long id, @RequestBody updateSchoolInfo dto) {
        return ResponseEntity.ok(schoolService.update(id, dto));
    }

}