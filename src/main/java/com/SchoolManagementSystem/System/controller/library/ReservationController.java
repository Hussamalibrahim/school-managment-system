package com.SchoolManagementSystem.System.controller.library;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.library.ReservationDto;
import com.SchoolManagementSystem.System.service.library.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation-book")
public class ReservationController extends BaseCrudController<ReservationDto> {
    public ReservationController(ReservationService reservationService) {
        super(reservationService);
    }

}