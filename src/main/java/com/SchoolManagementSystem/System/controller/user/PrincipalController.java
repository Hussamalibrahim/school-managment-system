package com.SchoolManagementSystem.System.controller.user;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/principals")
public class PrincipalController extends BaseCrudController<PrincipalDto> {

    public PrincipalController(PrincipalService service) {
        super(service);
    }
}