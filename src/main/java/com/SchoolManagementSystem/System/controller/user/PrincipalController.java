package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.System.service.academic.SchoolClassService;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/principal")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PRINCIPAL')")
public class PrincipalController {

    private final SchoolClassService schoolClassService;
    private final ClassScheduleService classScheduleService;
    private final PrincipalService principalService;

    // ADD Secretary, Librarian, Teacher Only
    @PostMapping("/create-user")
    public void createStaff(@RequestBody CreateUserRequest request) {
        principalService.createStaff(request);
    }

    @PostMapping("/assign-schedule")
    public ResponseEntity<ClassScheduleDto> assignSchedule(
            @RequestParam Long scheduleId,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId
    ) {
        return ResponseEntity.ok(classScheduleService.assignTeacher(scheduleId, teacherId, subjectId));
    }

    @PostMapping("/create-class")
    public void createClass(@RequestBody SchoolClassDto request) {
        schoolClassService.save(request);
    }

}