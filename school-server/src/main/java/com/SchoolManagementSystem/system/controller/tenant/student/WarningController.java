package com.SchoolManagementSystem.system.controller.tenant.student;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.service.student.WarningService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warnings")
public class WarningController extends BaseCrudController<WarningDto> {

    public WarningController(WarningService service) {
        super(service);
    }
}