package com.SchoolManagementSystem.system.controller.tenant.library;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.library.LibraryDto;
import com.SchoolManagementSystem.system.service.library.LibraryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController extends BaseCrudController<LibraryDto> {
    public LibraryController(LibraryService reservationService) {
        super(reservationService);
    }

}