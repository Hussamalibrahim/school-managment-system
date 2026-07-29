package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.AssessmentService;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.System.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AssessmentService assessmentService;
    private final TeacherService teacherService;
    private final ClassScheduleService classScheduleService;

    @GetMapping("/me")
    public ResponseEntity<TeacherDto> me(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(teacherService.getById(user.getRefId()));
    }

    @GetMapping("/my-students")
    public ResponseEntity<List<StudentDto>> myStudents(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(user.getRefId()));
    }

    @GetMapping("/my-schedule")
    public ResponseEntity<List<ClassScheduleDto>> mySchedule(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(classScheduleService.getByTeacher(user.getRefId()));
    }


    @GetMapping("/teacher/{teacherId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(teacherId));
    }
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeacher()
    {
        return ResponseEntity.ok(teacherService.getAll());
    }
}