package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.dto.academic.StudentEducationHistoryDto;
import com.SchoolManagementSystem.system.dto.academic.request.RegisterNextYearRequest;
import com.SchoolManagementSystem.system.dto.academic.response.*;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education-records")
@RequiredArgsConstructor
public class EducationRecordController {

    private final EducationRecordService educationRecordService;


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<EducationRecordDto> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                educationRecordService.getById(id)
        );
    }


    /*
     * Raw education records of a student.
     */
    @GetMapping("/student/{studentId}/records")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<EducationRecordDto>> getStudentRecords(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getStudentRecords(studentId)
        );
    }


    /*
     * Human-readable historical record.
     */
    @GetMapping("/student/{studentId}/history")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<StudentEducationHistoryDto>> getStudentHistory(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getStudentHistory(studentId)
        );
    }


    @GetMapping("/academic-year/{academicYearId}/passed")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<EducationRecordDto>> getPassedStudents(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getPassedStudents(
                                academicYearId
                        )
        );
    }


    @GetMapping("/academic-year/{academicYearId}/failed")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<EducationRecordDto>> getFailedStudents(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getFailedStudents(
                                academicYearId
                        )
        );
    }


    @GetMapping("/academic-year/{academicYearId}/statistics")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<AcademicYearStatisticsDto>
    getAcademicYearStatistics(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getAcademicYearStatistics(
                                academicYearId
                        )
        );
    }


    @GetMapping("/academic-year/{academicYearId}/average")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<Double> getAcademicYearAverage(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService
                        .getAcademicYearAverage(
                                academicYearId
                        )
        );
    }


    @GetMapping("/academic-year/{academicYearId}/top-students")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<TopStudentDto>> getTopStudents(
            @PathVariable Long academicYearId,
            @RequestParam(defaultValue = "7") int limit
    ) {

        return ResponseEntity.ok(
                educationRecordService.getTopStudents(
                        academicYearId,
                        limit
                )
        );
    }


    @GetMapping("/student/{studentId}/statistics")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<StudentAcademicStatisticsDto>
    getStudentStatistics(
            @PathVariable Long studentId,
            @RequestParam Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService.getStudentStatistics(
                        studentId,
                        academicYearId
                )
        );
    }


    @GetMapping("/academic-year/{academicYearId}/subjects/statistics")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<SubjectStatisticsDto>>
    getSubjectStatistics(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService.getSubjectStatistics(
                        academicYearId
                )
        );
    }


    /*
     * End of academic year:
     * calculate and create/update education records.
     */
    @PostMapping("/academic-year/{academicYearId}/generate")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<Void> generateEducationRecords(
            @PathVariable Long academicYearId
    ) {

        educationRecordService.generateEducationRecords(
                academicYearId
        );

        return ResponseEntity.ok().build();
    }


    /*
     * Promote all passed students in the school.
     */
    @PostMapping("/academic-year/{academicYearId}/promote")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<Void> promote(
            @PathVariable Long academicYearId
    ) {

        educationRecordService.promoteStudents(
                academicYearId
        );

        return ResponseEntity.ok().build();
    }


    /*
     * Exceptional/manual registration.
     */
    @PostMapping("/{id}/register-next-year")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<Void> registerNextYear(
            @PathVariable Long id,
            @RequestBody RegisterNextYearRequest request
    ) {

        educationRecordService.registerNextYear(
                id,
                request.targetClassId()
        );

        return ResponseEntity.ok().build();
    }
    @GetMapping("/student/{studentId}/year-statistics")
    public ResponseEntity<StudentYearStatisticsDto> getStudentYearStatistics(
            @PathVariable Long studentId,
            @RequestParam Long academicYearId
    ) {

        return ResponseEntity.ok(
                educationRecordService.getStudentYearStatistics(
                        studentId,
                        academicYearId
                )
        );
    }
    @GetMapping("/me/history")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<StudentEducationHistoryDto>> getMyHistory(
            @AuthenticationPrincipal UserPrincipal user
    ) {

        return ResponseEntity.ok(
                educationRecordService.getStudentHistory(
                        user.getRefId()
                )
        );
    }
    @GetMapping("/my-children/history")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<List<StudentEducationHistoryDto>> getMyChildrenHistory(
            @AuthenticationPrincipal UserPrincipal user
    ) {

        return ResponseEntity.ok(
                educationRecordService.getGuardianChildrenHistory(
                        user.getRefId()
                )
        );
    }
}