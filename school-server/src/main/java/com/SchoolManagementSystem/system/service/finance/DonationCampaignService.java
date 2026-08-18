package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.dto.finance.DonationCampaignDto;
import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationCampaignRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DonationCampaignService {

    DonationCampaignDto save(DonationCampaignRequest request);

    DonationCampaignDto update(Long id, DonationCampaignRequest request);

    DonationCampaignDto getById(Long id);

    List<DonationCampaignDto> getAll();

    void delete(Long id);

    List<DonationDto> getCampaignDonations(Long campaignId);
}