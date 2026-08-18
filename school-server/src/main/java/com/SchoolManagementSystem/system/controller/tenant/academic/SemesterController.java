package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.dto.academic.request.SemesterUpdateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.UpdateTwoSemesterRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeeStructureDto;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PutMapping("/academic-year/{academicYearId}")
    public void updateSemester(@PathVariable Long academicYearId, @RequestBody SemesterUpdateRequest semesterUpdateRequest) {
        semesterService.updateSemester(academicYearId, semesterUpdateRequest);
    }
    @PutMapping("/academic-year/{academicYearId}/semesters")
    public void updateSemester(@PathVariable Long academicYearId, @RequestBody UpdateTwoSemesterRequest updateTwoSemesterRequest) {
        semesterService.updateTwoSemester(academicYearId, updateTwoSemesterRequest);
    }
}