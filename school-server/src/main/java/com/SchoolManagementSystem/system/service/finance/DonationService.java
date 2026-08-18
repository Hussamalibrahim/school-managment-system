package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;

import java.util.List;

public interface DonationService {

    DonationDto donate(UserPrincipal principal, Long campaignId, DonationRequest request);

    List<DonationDto> getMyDonations(UserPrincipal principal);

    List<DonationDto> getCampaignDonations(Long campaignId);

    DonationDto getById(Long id);

    void delete(Long id);
}