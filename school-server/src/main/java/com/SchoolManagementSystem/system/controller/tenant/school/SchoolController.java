package com.SchoolManagementSystem.system.controller.tenant.school;

import com.SchoolManagementSystem.system.dto.request.DefineSchool;
import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
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