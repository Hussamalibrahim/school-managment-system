package com.SchoolManagementSystem.System.controller.library;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.library.BorrowDto;
import com.SchoolManagementSystem.System.service.library.BorrowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrowed-book")
public class BorrowController extends BaseCrudController<BorrowDto> {
    public BorrowController(BorrowService borrowService) {
        super(borrowService);
    }

}