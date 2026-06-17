package com.SchoolManagementSystem.System.controller.school;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.school.SchoolSettingsDto;
import com.SchoolManagementSystem.System.service.school.SchoolSettingsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-settings")
public class SchoolSettingsController extends BaseCrudController<SchoolSettingsDto> {
    public SchoolSettingsController(SchoolSettingsService schoolSettingsService) {
        super(schoolSettingsService);
    }

}