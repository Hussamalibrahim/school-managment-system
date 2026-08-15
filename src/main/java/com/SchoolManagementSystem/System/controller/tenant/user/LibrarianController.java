package com.SchoolManagementSystem.System.controller.tenant.user;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.user.LibrarianDto;
import com.SchoolManagementSystem.System.service.user.LibrarianService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/librarians")
public class LibrarianController extends BaseCrudController<LibrarianDto> {

    public LibrarianController(LibrarianService service) {
        super(service);
    }
}