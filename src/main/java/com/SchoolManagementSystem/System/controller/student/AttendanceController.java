package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.student.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAttendance(@RequestBody AttendanceRequest request) {
        attendanceService.saveAttendance(request);
    }

    @PutMapping("/{id}")
    public void saveAttendance(@PathVariable Long id, @RequestBody AttendanceCreateRequest request) {
        attendanceService.update(id, request);
    }

    @GetMapping("/{id}")
    public AttendanceDto getById(@PathVariable Long id) {
        return attendanceService.getById(id);
    }

    @GetMapping
    public List<AttendanceDto> getAll() {
        return attendanceService.getAll();
    }

    @GetMapping("/student/{studentId}")
    public List<AttendanceDto> getStudentAttendance(
            @PathVariable Long studentId) {

        return attendanceService.getStudentAttendance(studentId);
    }

    //TODO should be removed
    @PostMapping("/test")
    public void save(@RequestBody AttendanceCreateRequest request) {
        attendanceService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        attendanceService.delete(id);
    }


}