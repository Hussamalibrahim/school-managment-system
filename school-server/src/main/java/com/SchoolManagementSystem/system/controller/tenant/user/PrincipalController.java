package com.SchoolManagementSystem.system.controller.tenant.user;

import com.SchoolManagementSystem.system.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.system.dto.user.PrincipalDto;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.system.service.academic.SchoolClassService;
import com.SchoolManagementSystem.system.service.user.PrincipalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/principal")
@RequiredArgsConstructor
public class PrincipalController {

    private final SchoolClassService schoolClassService;
    private final ClassScheduleService classScheduleService;
    private final PrincipalService principalService;

    // ADD Secretary, Librarian, Teacher Only
    @PostMapping("/create-user")
    public ResponseEntity<Void> createStaff(@RequestBody CreateUserRequest request) {
        principalService.createStaff(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/assign-schedule")
    public ResponseEntity<ClassScheduleDto> assignSchedule(
            @RequestParam Long scheduleId,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classScheduleService.assignTeacher(scheduleId, teacherId, subjectId));
    }

    @PostMapping("/create-class")
    public ResponseEntity<Void> createClass(@RequestBody SchoolClassDto request) {
        schoolClassService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<PrincipalDto> me(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(principalService.getById(user.getRefId()));
    }
}