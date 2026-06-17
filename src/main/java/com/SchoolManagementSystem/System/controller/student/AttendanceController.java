package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.service.student.AttendanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController extends BaseCrudController<AttendanceDto> {

    public AttendanceController(AttendanceService service) {
        super(service);
    }
}