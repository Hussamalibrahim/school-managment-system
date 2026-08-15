package com.SchoolManagementSystem.system.controller.tenant.library;

import com.SchoolManagementSystem.system.controller.BaseCrudController;
import com.SchoolManagementSystem.system.dto.library.BorrowDto;
import com.SchoolManagementSystem.system.service.library.BorrowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrowed-book")
public class BorrowController extends BaseCrudController<BorrowDto> {
    public BorrowController(BorrowService borrowService) {
        super(borrowService);
    }

}