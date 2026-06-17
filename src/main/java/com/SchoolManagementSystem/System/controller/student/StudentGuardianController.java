package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import com.SchoolManagementSystem.System.service.student.WarningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/api/student-guardian")
public class StudentGuardianController extends BaseCrudController<StudentGuardianDto> {

    public StudentGuardianController(StudentGuardianService studentGuardianService) {
        super(studentGuardianService);
    }


    @PostMapping("/connect/{studentId}/{guardianId}")
    public ResponseEntity<StudentGuardianDto> connectStudentToGuardian(
            @PathVariable Long studentId,
            @PathVariable Long guardianId,
            @RequestParam(defaultValue = "false") Boolean primaryGuardian)
    {
        return ResponseEntity.ok(
                ((StudentGuardianService) service)
                        .connectStudentToGuardian(studentId, guardianId, primaryGuardian)
        );
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getGuardiansByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                ((StudentGuardianService) service)
                        .getGuardiansByStudent(studentId)
        );
    }
    @GetMapping("/guardian/{guardianId}")
    public ResponseEntity<?> getStudentsByGuardian(
            @PathVariable Long guardianId) {

        return ResponseEntity.ok(
                ((StudentGuardianService) service)
                        .getStudentsByGuardian(guardianId)
        );
    }
}