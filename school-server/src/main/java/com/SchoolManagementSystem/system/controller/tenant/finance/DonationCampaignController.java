package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.DonationCampaignDto;
import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationCampaignRequest;
import com.SchoolManagementSystem.system.service.finance.DonationCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation-campaigns")
public class DonationCampaignController {

    private final DonationCampaignService donationCampaignService;

    @PostMapping
    public ResponseEntity<DonationCampaignDto> save(@RequestBody DonationCampaignRequest request) {

        return ResponseEntity.ok(donationCampaignService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationCampaignDto> update(@PathVariable Long id,
            @RequestBody DonationCampaignRequest request) {

        return ResponseEntity.ok(donationCampaignService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationCampaignDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(donationCampaignService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DonationCampaignDto>> getAll() {

        return ResponseEntity.ok(donationCampaignService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        donationCampaignService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{campaignId}/donation")
    public ResponseEntity<List<DonationDto>> getCampaignDonations(
            @PathVariable Long campaignId) {

        return ResponseEntity.ok(donationCampaignService.getCampaignDonations(campaignId)
        );
    }
}