package com.SchoolManagementSystem.system.controller.tenant.student;

import com.SchoolManagementSystem.system.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.system.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.system.dto.student.AttendanceDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.system.service.student.AttendanceService;
import com.SchoolManagementSystem.system.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ClassScheduleService classScheduleService;
    private final AttendanceService attendanceService;

    @PutMapping("/{studentId}/assign-class/{classId}")
    public ResponseEntity<StudentDto> assignClass(
            @PathVariable Long studentId,
            @PathVariable Long classId) {

        return ResponseEntity.ok(studentService.assignClass(studentId, classId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<StudentDto> me(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(studentService.getById(user.getRefId()));
    }

    @GetMapping("/me-subject")
    public ResponseEntity<List<SubjectNameDto>> meSubject(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(studentService.getNamesSubjectByGradeAndSemester(user.getRefId()));
    }

    @GetMapping("/me-attendance")
    public List<AttendanceDto> getMyAttendance(@AuthenticationPrincipal UserPrincipal user) {

        return attendanceService.getMyAttendance(user.getRefId());
    }
    @GetMapping("/me-schedule")
    public ResponseEntity<List<ClassScheduleDto>> getMySchedule(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(classScheduleService.getMySchedule(user));
    }
}