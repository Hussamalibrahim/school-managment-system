package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.ClassDto;
import com.SchoolManagementSystem.System.service.academic.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class ClassController extends BaseCrudController<ClassDto> {

    public ClassController(ClassService service) {
        super(service);
    }

}