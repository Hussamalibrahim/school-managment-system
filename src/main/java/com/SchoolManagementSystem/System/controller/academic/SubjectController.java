package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('PRINCIPAL')")
@RequestMapping("/api/subjects")
public class SubjectController extends BaseCrudController<SubjectDto> {

    public SubjectController(SubjectService service) {
        super(service);
    }

}