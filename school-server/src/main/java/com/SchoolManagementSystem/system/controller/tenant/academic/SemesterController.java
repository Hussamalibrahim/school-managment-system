package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.academic.SemesterDto;
import com.SchoolManagementSystem.system.service.academic.SemesterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semesters")
public class SemesterController extends BaseCrudController<SemesterDto> {

    public SemesterController(SemesterService service) {
        super(service);
    }

}