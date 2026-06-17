package com.SchoolManagementSystem.System.controller.library;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.library.LibraryBookDto;
import com.SchoolManagementSystem.System.service.library.LibraryBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class LibraryBookController extends BaseCrudController<LibraryBookDto> {
    public LibraryBookController(LibraryBookService libraryBookService) {
        super(libraryBookService);
    }

}