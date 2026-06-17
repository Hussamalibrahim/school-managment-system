package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.System.service.academic.AssessmentResultService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment-results")
public class AssessmentResultController extends BaseCrudController<AssessmentResultDto> {

    public AssessmentResultController(AssessmentResultService service) {
        super(service);
    }
}