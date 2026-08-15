package com.SchoolManagementSystem.System.controller.tenant.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.dto.academic.request.AssessmentCreateRequest;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.AssessmentMapper;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentDto> create(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody AssessmentCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assessmentService.save(user, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentDto> update(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody AssessmentCreateRequest request) {

        return ResponseEntity.ok(assessmentService.update(id, user, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(assessmentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssessmentDto>> getAll() {

        return ResponseEntity.ok(assessmentService.getAll());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AssessmentDto>> getTeacherAssessments(
            @PathVariable Long teacherId) {

        return ResponseEntity.ok(
                assessmentService.getTeacherAssessments(teacherId)
        );
    }

    @GetMapping("/me/{teacherId}")
    public ResponseEntity<List<AssessmentDto>> getMeAssessments(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(
                assessmentService.getTeacherAssessments(user)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<AssessmentDto>> getMyAssessments(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(assessmentService.getTeacherAssessments(user.getRefId()));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<AssessmentDto>> getSubjectAssessments(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                assessmentService.getSubjectAssessments(subjectId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assessmentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedule/{classScheduleId}")
    public ResponseEntity<List<AssessmentDto>> getClassScheduleAssessments(
            @PathVariable Long classScheduleId,
            @RequestParam Long semesterId) {

        return ResponseEntity.ok(
                assessmentService.getClassScheduleAssessments(classScheduleId, semesterId));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<AssessmentDto>> getSchoolClassAssessments(
            @PathVariable Long classId,
            @RequestParam Long semesterId) {

        return ResponseEntity.ok(
                assessmentService.getSchoolClassAssessments(classId, semesterId));
    }

    @GetMapping("/teacher/{teacherId}/subject")
    public ResponseEntity<List<AssessmentDto>> getTeacherSubjectAssessments(
            @PathVariable Long teacherId) {

        return ResponseEntity.ok(
                assessmentService.getTeacherSubjectAssessments(teacherId));
    }

}