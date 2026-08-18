package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.request.FeeStructureRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeeStructureDto;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.service.finance.FeeStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fee-structures")
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping
    public ResponseEntity<FeeStructureDto> save(@RequestBody FeeStructureRequest request) {

        return ResponseEntity.ok(feeStructureService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeStructureDto> update(
            @PathVariable Long id,
            @RequestBody FeeStructureRequest request) {

        return ResponseEntity.ok(feeStructureService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeStructureDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(feeStructureService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeeStructureDto>> getAll() {

        return ResponseEntity.ok(feeStructureService.getAll());
    }

    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<FeeStructureDto>> getBySemester(@PathVariable Long semesterId) {

        return ResponseEntity.ok(feeStructureService.getBySemester(semesterId));
    }

    @GetMapping("/semester/{semesterId}/grade/{gradeLevel}")
    public ResponseEntity<List<FeeStructureDto>> getBySemesterAndGrade(@PathVariable Long semesterId, @PathVariable GradeLevel gradeLevel) {

        return ResponseEntity.ok(feeStructureService.getBySemesterAndGrade(semesterId, gradeLevel));
    }

    @GetMapping("/current-year/semester/{semesterName}/grade/{gradeLevel}")
    public ResponseEntity<List<FeeStructureDto>> getBySemesterAndGradeForCurrentYear(@PathVariable SemesterName semesterName, @PathVariable GradeLevel gradeLevel) {

        return ResponseEntity.ok(feeStructureService.getBySemesterAndGradeForCurrentYear(semesterName, gradeLevel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        feeStructureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}