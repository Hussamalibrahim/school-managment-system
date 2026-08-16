package com.SchoolManagementSystem.system.controller.tenant.user;

import com.SchoolManagementSystem.system.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.dto.student.request.CreateWarningDto;
import com.SchoolManagementSystem.system.dto.user.TeacherDto;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.AssessmentService;
import com.SchoolManagementSystem.system.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.system.service.academic.SchoolClassService;
import com.SchoolManagementSystem.system.service.student.StudentService;
import com.SchoolManagementSystem.system.service.student.WarningService;
import com.SchoolManagementSystem.system.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AssessmentService assessmentService;
    private final TeacherService teacherService;
    private final ClassScheduleService classScheduleService;
    private final SchoolClassService schoolClassService;
    private final StudentService studentService;

    @GetMapping("/me")
    public ResponseEntity<TeacherDto> me(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(teacherService.getById(user.getRefId()));
    }

    @GetMapping("/my-students")
    public ResponseEntity<List<StudentDto>> myStudents(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(user.getRefId()));
    }

    @GetMapping("/my-classes")
    public ResponseEntity<List<SchoolClassDto>> myClasses(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(schoolClassService.getBySchoolClassByTeacher(user.getRefId()));
    }
    @GetMapping("/my-schedule")
    public ResponseEntity<List<ClassScheduleDto>> mySchedule(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(classScheduleService.getByTeacher(user.getRefId()));
    }

    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(classScheduleService.getStudentsByTeacher(teacherId));
    }
    @GetMapping("/class/{classId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsTeacherByClassId(@PathVariable Long classId
    ,@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(studentService.getStudentsTeacherByClassId(classId, user.getRefId()));
    }
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeacher()
    {
        return ResponseEntity.ok(teacherService.getAll());
    }


}