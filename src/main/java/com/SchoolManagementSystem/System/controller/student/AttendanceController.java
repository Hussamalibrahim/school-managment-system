package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.config.AttendanceStatistics;
import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceRequest;
import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.AttendanceService;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private  final StudentGuardianService studentGuardianService;

    @PostMapping
    public ResponseEntity<Void> saveAttendance(@RequestBody AttendanceRequest request) {
        attendanceService.saveAttendance(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAttendance(@PathVariable Long id, @RequestBody AttendanceCreateRequest request) {
        attendanceService.update(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDto>> getAll() {
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDto>> getStudentAttendance(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(attendanceService.getStudentAttendance(studentId));
    }

    //TODO should be removed
    @PostMapping("/test")
    public ResponseEntity<Void> save(@RequestBody AttendanceCreateRequest request) {
        attendanceService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/student/{studentId}/statistics")
    public ResponseEntity<AttendanceStatistics> getStatistics(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long studentId) {

        if (user.getRole().equals(Role.STUDENT) && !(user.getRefId().equals(studentId))) {
            throw new ValidationException(ErrorCode.ACCESS_DENIED);
        }
        if (user.getRole().equals(Role.GUARDIAN) && !studentGuardianService.isStudentBelongsToGuardian(studentId,user.getRefId())) {
            throw new ValidationException(ErrorCode.ACCESS_DENIED);
        }

        return ResponseEntity.ok(attendanceService.getAttendanceStatistics(studentId));
    }
    @GetMapping("/exceeded")
    public ResponseEntity<List<StudentDto>> getExceededStudents(
            @RequestParam AttendanceStatus status,
            @RequestParam long limit) {

        return ResponseEntity.ok(attendanceService.getStudentsExceededAttendanceStatus(status, limit));
    }

    @GetMapping("/guardian")
    public ResponseEntity<List<AttendanceDto>> getGuardianAttendance(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(attendanceService.getGuardianStudentsAttendance(user.getRefId()));
    }

    @GetMapping("/guardian/student/{studentId}")
    public ResponseEntity<List<AttendanceDto>> getGuardianStudentAttendance(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long studentId) {


        return ResponseEntity.ok(
                attendanceService.getGuardianStudentAttendance(
                        user.getRefId(),
                        studentId
                )
        );
    }

}