package com.SchoolManagementSystem.System.controller.school;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.library.SchoolBookDto;
import com.SchoolManagementSystem.System.service.library.SchoolBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-book")
public class SchoolBookController extends BaseCrudController<SchoolBookDto> {
    public SchoolBookController(SchoolBookService academicYearService) {
        super(academicYearService);
    }

}