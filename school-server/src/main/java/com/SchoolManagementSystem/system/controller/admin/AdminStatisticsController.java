package com.SchoolManagementSystem.system.controller.admin;

import com.SchoolManagementSystem.system.dto.admin.AdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;
import com.SchoolManagementSystem.system.service.admin.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
public class AdminStatisticsController {

    private final AdminStatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<AdminStatisticsDto> getStatistics() {

        return ResponseEntity.ok(statisticsService.getStatistics());
    }


    @GetMapping("/schools")
    public ResponseEntity<List<SchoolAdminStatisticsDto>> getSchoolsStatistics() {
        return ResponseEntity.ok(statisticsService.getSchoolsStatistics());
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<SchoolAdminStatisticsDto> getSchoolStatistics(@PathVariable Long schoolId) {

        return ResponseEntity.ok(statisticsService.getSchoolStatistics(schoolId));
    }
}