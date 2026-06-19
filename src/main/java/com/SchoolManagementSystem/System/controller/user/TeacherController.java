package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.System.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final ClassScheduleService classScheduleService;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/me")
    public ResponseEntity<TeacherDto> me(Authentication auth) {

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        return ResponseEntity.ok(teacherService.getById(user.getRefId()));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/my-students")
    public ResponseEntity<List<StudentDto>> myStudents(Authentication auth) {
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(user.getRefId()));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/my-schedule")
    public ResponseEntity<List<ClassScheduleDto>> mySchedule(Authentication auth) {

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        return ResponseEntity.ok(classScheduleService.getByTeacher(user.getRefId()));
    }
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ClassScheduleDto>> getByClass(@PathVariable Long classId)
    {
        return ResponseEntity.ok(classScheduleService.getByClass(classId));
    }
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teacher/{teacherId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByTeacher(@PathVariable Long teacherId)
    {
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(teacherId));
    }
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeacher()
    {
        return ResponseEntity.ok(teacherService.getAll());
    }
}