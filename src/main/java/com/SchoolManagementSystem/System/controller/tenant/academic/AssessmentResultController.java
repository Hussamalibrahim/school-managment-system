package com.SchoolManagementSystem.System.controller.tenant.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.service.academic.AssessmentResultService;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.SchoolManagementSystem.System.dto.academic.request.AssessmentResultRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assessment-results")
public class AssessmentResultController {

    private final AssessmentResultService assessmentResultService;
    private final StudentGuardianService studentGuardianService;

    @PostMapping
    public ResponseEntity<Void> saveResults(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody AssessmentResultRequest request) {

        assessmentResultService.saveResults(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateResults(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody AssessmentResultRequest request) {

        assessmentResultService.updateResults(user, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResultDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assessmentResultService.getById(id)
        );
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<AssessmentResultDto>> getAssessmentResults(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                assessmentResultService.getAssessmentResults(assessmentId)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssessmentResultDto>> getStudentResults(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                assessmentResultService.getStudentResults(studentId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        assessmentResultService.delete(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<List<AssessmentResultDto>> getMyResults(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(assessmentResultService.getStudentResults(user.getRefId()));
    }
    @GetMapping("/guardian/student/{studentId}")
    public ResponseEntity<List<AssessmentResultDto>> getStudentResultsForGuardian(
            @PathVariable Long studentId,
            @AuthenticationPrincipal UserPrincipal user) {

        if (!studentGuardianService.isStudentBelongsToGuardian(studentId, user.getRefId())) {
            throw new ValidationException(ErrorCode.UNAUTHENTICATED);
        }

        return ResponseEntity.ok(assessmentResultService.getStudentResults(studentId));
    }
    @GetMapping("/guardian/me")
    public ResponseEntity<List<StudentDto>> getMyStudents(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(studentGuardianService.getGuardianStudents(user.getRefId()));
    }
    //TODO School class student
}