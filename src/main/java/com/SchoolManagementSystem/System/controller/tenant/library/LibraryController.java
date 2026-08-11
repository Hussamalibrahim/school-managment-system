package com.SchoolManagementSystem.System.controller.library;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.library.LibraryDto;
import com.SchoolManagementSystem.System.service.library.LibraryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController extends BaseCrudController<LibraryDto> {
    public LibraryController(LibraryService reservationService) {
        super(reservationService);
    }

}