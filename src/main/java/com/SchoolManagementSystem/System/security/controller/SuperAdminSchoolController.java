package com.SchoolManagementSystem.System.security.controller;

import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.System.mapper.school.SchoolMapper;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SuperAdminSchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<List<SchoolDto>> getAllSchools() {
        return ResponseEntity.ok(schoolService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<SchoolDto> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(SchoolMapper.toDto(schoolService.findByCode(code)));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerSchool(@RequestBody SchoolRegisterRequest request) {
        schoolService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable Long id, @RequestBody updateSchoolInfo dto) {
        return ResponseEntity.ok(schoolService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
