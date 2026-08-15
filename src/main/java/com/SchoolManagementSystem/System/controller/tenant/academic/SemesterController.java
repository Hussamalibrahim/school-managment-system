package com.SchoolManagementSystem.System.controller.tenant.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.service.academic.SemesterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semesters")
public class SemesterController extends BaseCrudController<SemesterDto> {

    public SemesterController(SemesterService service) {
        super(service);
    }

}