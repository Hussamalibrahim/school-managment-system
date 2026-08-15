package com.SchoolManagementSystem.System.controller.tenant.school;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.System.service.school.AcademicYearService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academic-year")
public class AcademicYearController extends BaseCrudController<AcademicYearDto> {
    public AcademicYearController(AcademicYearService academicYearService) {
        super(academicYearService);
    }

}