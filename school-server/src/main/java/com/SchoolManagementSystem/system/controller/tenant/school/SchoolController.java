package com.SchoolManagementSystem.system.controller.tenant.school;

import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.dto.school.SchoolStatisticsDto;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.service.school.SchoolService;
import com.SchoolManagementSystem.system.service.school.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController{

    private final StatisticsService statisticsService;
    private final SchoolService schoolService;

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getById(id));
    }
    @GetMapping
    public ResponseEntity<SchoolDto> getByUrl() {
        return ResponseEntity.ok(schoolService.findByUrl());
    }
    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> update(@PathVariable Long id, @RequestBody updateSchoolInfo dto) {
        return ResponseEntity.ok(schoolService.update(id, dto));
    }
    @GetMapping("/available-grades")
    public ResponseEntity<Set<GradeLevel>> availableGrades() {
        return ResponseEntity.ok(schoolService.availableGrades());
    }
    @GetMapping("/statistics")
    public ResponseEntity<SchoolStatisticsDto> getSchoolStatistics() {

        return ResponseEntity.ok(statisticsService.getSchoolsStatistics());
    }
}