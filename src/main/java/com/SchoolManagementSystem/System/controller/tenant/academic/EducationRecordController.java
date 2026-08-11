package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.System.service.academic.EducationRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/education-records")
public class EducationRecordController extends BaseCrudController<EducationRecordDto> {

    public EducationRecordController(EducationRecordService service) {
        super(service);
    }
}