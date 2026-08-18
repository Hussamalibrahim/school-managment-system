package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.dto.finance.request.DonationRequest;
import com.SchoolManagementSystem.system.entity.enumeration.DonationStatus;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.finance.Donation;
import com.SchoolManagementSystem.system.entity.finance.DonationCampaign;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.finance.DonationMapper;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.finance.DonationCampaignRepository;
import com.SchoolManagementSystem.system.repository.finance.DonationRepository;
import com.SchoolManagementSystem.system.repository.user.GuardianRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.finance.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationServiceImpl
        implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationCampaignRepository campaignRepository;
    private final GuardianRepository guardianRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public DonationDto donate(
            UserPrincipal principal,
            Long campaignId,
            DonationRequest request) {

        if (principal.getRole() != Role.GUARDIAN) {
            throw new ValidationException(ErrorCode.ACCESS_DENIED);
        }

        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ValidationException(ErrorCode.INVALID_DONATION_AMOUNT);
        }

        if (request.paymentMethod() == null) {
            throw new ValidationException(ErrorCode.INVALID_PAYMENT_METHOD);
        }

        DonationCampaign campaign = campaignRepository.findById(campaignId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.DONATION_CAMPAIGN_NOT_FOUND));

        if (!Boolean.TRUE.equals(campaign.getActive())) {
            throw new ValidationException(ErrorCode.DONATION_CAMPAIGN_NOT_ACTIVE);
        }

        Guardian guardian =
                guardianRepository.findById(principal.getRefId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));

        Donation donation = new Donation();

        donation.setCampaign(campaign);
        donation.setGuardian(guardian);
        donation.setAmount(request.amount());
        donation.setPaymentMethod(request.paymentMethod());
        donation.setNotes(request.notes());
        donation.setDonationDate(LocalDateTime.now());

        donation.setStatus(DonationStatus.COMPLETED);

        donation = donationRepository.save(donation);

        return DonationMapper.toDto(donation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationDto> getMyDonations(UserPrincipal principal) {

        if (principal.getRole() != Role.GUARDIAN) {
            throw new ValidationException(ErrorCode.ACCESS_DENIED);
        }

        return donationRepository
                .findByGuardianIdOrderByDonationDateDesc(
                        principal.getRefId()
                )
                .stream()
                .map(DonationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationDto> getCampaignDonations(Long campaignId) {

        campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DONATION_CAMPAIGN_NOT_FOUND));

        return donationRepository
                .findByCampaignIdOrderByDonationDateDesc(
                        campaignId
                )
                .stream()
                .map(DonationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DonationDto getById(Long id) {

        return donationRepository.findById(id)
                .map(DonationMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DONATION_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        Donation donation = donationRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.DONATION_NOT_FOUND));

        donationRepository.delete(donation);
    }
}