package com.SchoolManagementSystem.system.controller.tenant.school;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.system.service.school.AcademicYearService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academic-year")
public class AcademicYearController extends BaseCrudController<AcademicYearDto> {
    public AcademicYearController(AcademicYearService academicYearService) {
        super(academicYearService);
    }

}