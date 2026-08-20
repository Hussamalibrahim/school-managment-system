//package com.SchoolManagementSystem.system.controller.tenant.academic;
//
//import com.SchoolManagementSystem.system.controller.BaseCrudController;
//import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
//import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/education-records")
//@RequiredArgsConstructor
//public class EducationRecordController {
//
//    private final EducationRecordService educationRecordService;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EducationRecordDto> getById(@PathVariable Long id) {
//
//        return ResponseEntity.ok(educationRecordService.getById(id));
//    }
//
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<EducationRecordDto>> getStudentRecords(@PathVariable Long studentId) {
//
//        return ResponseEntity.ok(educationRecordService.getStudentRecords(studentId));
//    }
//
//    @GetMapping("/student/{studentId}/year/{academicYearId}")
//    public ResponseEntity<EducationRecordDto> getStudentRecord(
//            @PathVariable Long studentId,
//            @PathVariable Long academicYearId) {
//
//        return ResponseEntity.ok(educationRecordService.getStudentRecord(studentId, academicYearId));
//    }
//
//    @PostMapping("/finalize/{academicYearId}")
//    @PreAuthorize("hasRole('PRINCIPAL')")
//    public ResponseEntity<Void> finalizeAcademicYear(@PathVariable Long academicYearId) {
//
//        educationRecordService.finalizeAcademicYear(academicYearId);
//
//        return ResponseEntity.noContent().build();
//    }
//}