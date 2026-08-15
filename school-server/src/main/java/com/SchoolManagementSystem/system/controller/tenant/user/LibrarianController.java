package com.SchoolManagementSystem.system.controller.tenant.user;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.user.LibrarianDto;
import com.SchoolManagementSystem.system.service.user.LibrarianService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/librarians")
public class LibrarianController extends BaseCrudController<LibrarianDto> {

    public LibrarianController(LibrarianService service) {
        super(service);
    }
}