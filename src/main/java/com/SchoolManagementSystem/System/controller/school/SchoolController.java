package com.SchoolManagementSystem.System.controller.school;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController extends BaseCrudController<SchoolDto> {
    public SchoolController(SchoolService schoolService) {
        super(schoolService);
    }

}