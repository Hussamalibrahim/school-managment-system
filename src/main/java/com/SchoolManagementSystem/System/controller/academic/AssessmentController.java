package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.service.academic.AssessmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController extends BaseCrudController<AssessmentDto> {

    public AssessmentController(AssessmentService service) {
        super(service);
    }
}