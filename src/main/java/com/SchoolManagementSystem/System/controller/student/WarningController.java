package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.service.student.WarningService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warnings")
public class WarningController extends BaseCrudController<WarningDto> {

    public WarningController(WarningService service) {
        super(service);
    }
}