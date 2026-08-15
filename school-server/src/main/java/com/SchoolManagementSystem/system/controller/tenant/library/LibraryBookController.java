package com.SchoolManagementSystem.system.controller.tenant.library;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.library.LibraryBookDto;
import com.SchoolManagementSystem.system.service.library.LibraryBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class LibraryBookController extends BaseCrudController<LibraryBookDto> {
    public LibraryBookController(LibraryBookService libraryBookService) {
        super(libraryBookService);
    }

}