package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

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


}