package com.SchoolManagementSystem.system.controller.tenant.school;

import com.SchoolManagementSystem.system.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.system.service.school.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/academic-year")
public class AcademicYearController {
    private final AcademicYearService academicYearService;

    @PostMapping
    public ResponseEntity<Void> createAcademicYear(@RequestBody AcademicYearDto academicYearDto) {

        academicYearService.createAcademicYear(academicYearDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearDto> getAcademicYearById(@PathVariable Long id) {

        return ResponseEntity.ok(academicYearService.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<AcademicYearDto>> getAcademicYearById() {

        return ResponseEntity.ok(academicYearService.getAll());
    }
}