package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.DonationCampaignDto;
import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationCampaignRequest;
import com.SchoolManagementSystem.system.entity.enumeration.DonationStatus;
import com.SchoolManagementSystem.system.entity.finance.Donation;
import com.SchoolManagementSystem.system.entity.finance.DonationCampaign;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.finance.DonationMapper;
import com.SchoolManagementSystem.system.repository.finance.DonationCampaignRepository;
import com.SchoolManagementSystem.system.repository.finance.DonationRepository;
import com.SchoolManagementSystem.system.service.finance.DonationCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationCampaignServiceImpl implements DonationCampaignService {

    private final DonationCampaignRepository campaignRepository;
    private final DonationRepository donationRepository;

    @Override
    public DonationCampaignDto save(DonationCampaignRequest request) {

        validate(request);

        DonationCampaign campaign = new DonationCampaign();

        campaign.setName(request.name());
        campaign.setDescription(request.description());
        campaign.setTargetAmount(request.targetAmount());
        campaign.setStartDate(request.startDate());
        campaign.setEndDate(request.endDate());
        campaign.setActive(true);

        campaign = campaignRepository.save(campaign);

        return toDto(campaign);
    }

    @Override
    public DonationCampaignDto update(Long id, DonationCampaignRequest request) {

        DonationCampaign campaign = findCampaign(id);

        validate(request);

        campaign.setName(request.name());
        campaign.setDescription(request.description());
        campaign.setTargetAmount(request.targetAmount());
        campaign.setStartDate(request.startDate());
        campaign.setEndDate(request.endDate());

        return toDto(campaignRepository.save(campaign));
    }

    @Override
    @Transactional(readOnly = true)
    public DonationCampaignDto getById(Long id) {

        return toDto(findCampaign(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationCampaignDto> getAll() {

        return campaignRepository
                .findByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        DonationCampaign campaign =
                findCampaign(id);
        //Dont Delete it
        campaign.setActive(false);
        campaignRepository.save(campaign);
    }

    private DonationCampaign findCampaign(Long id) {

        return campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DONATION_CAMPAIGN_NOT_FOUND));
    }

    private void validate(DonationCampaignRequest request) {

        if (request.name() == null || request.name().isBlank()) {
            throw new ValidationException(ErrorCode.INVALID_DONATION_CAMPAIGN);
        }

        if (request.targetAmount() == null || request.targetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(ErrorCode.INVALID_DONATION_TARGET);
        }

        if (request.startDate() == null) {
            throw new ValidationException(ErrorCode.INVALID_DONATION_DATE);
        }

        if (request.endDate() != null && !request.endDate().isAfter(request.startDate())) {
            throw new ValidationException(ErrorCode.INVALID_DONATION_DATE);
        }
    }

    private DonationCampaignDto toDto(DonationCampaign campaign) {

        BigDecimal raisedAmount = donationRepository.findByCampaignIdAndStatus(
                        campaign.getId(),
                        DonationStatus.COMPLETED)
                .stream()
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        BigDecimal remainingAmount = campaign.getTargetAmount()
                .subtract(raisedAmount)
                .max(BigDecimal.ZERO);

        return new DonationCampaignDto(
                campaign.getId(),
                campaign.getName(),
                campaign.getDescription(),
                campaign.getTargetAmount(),
                raisedAmount,
                remainingAmount,
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getActive());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationDto> getCampaignDonations(Long campaignId) {

        if(!campaignRepository.existsById(campaignId)){
            throw new NotFoundException(ErrorCode.DONATION_CAMPAIGN_NOT_FOUND);
        }

        return donationRepository.findByCampaignIdOrderByDonationDateDesc(campaignId)
                .stream()
                .map(DonationMapper::toDto)
                .toList();
    }
}