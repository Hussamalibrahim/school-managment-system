package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.finance.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations")
public class DonationController {

    private final DonationService donationService;

    @PostMapping("/campaign/{campaignId}")
    public ResponseEntity<DonationDto> donate(
            @PathVariable Long campaignId,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody DonationRequest request) {

        return ResponseEntity.ok(donationService.donate(user, campaignId, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<DonationDto>> getMyDonations(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(donationService.getMyDonations(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(donationService.getById(id));
    }
}