package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/education-records")
public class EducationRecordController extends BaseCrudController<EducationRecordDto> {

    public EducationRecordController(EducationRecordService service) {
        super(service);
    }
}