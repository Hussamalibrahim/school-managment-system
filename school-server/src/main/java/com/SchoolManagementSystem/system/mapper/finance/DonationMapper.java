package com.SchoolManagementSystem.system.mapper.finance;

import com.SchoolManagementSystem.system.dto.finance.DonationDto;
import com.SchoolManagementSystem.system.entity.finance.Donation;

public final class DonationMapper {

    private DonationMapper() {
    }

    public static DonationDto toDto(
            Donation donation) {

        return new DonationDto(
                donation.getId(),
                donation.getCampaign().getId(),
                donation.getGuardian().getId(),
                donation.getGuardian().getFirstName()
                        + " "
                        + donation.getGuardian().getLastName(),
                donation.getAmount(),
                donation.getPaymentMethod(),
                donation.getStatus(),
                donation.getDonationDate(),
                donation.getReceiptNumber(),
                donation.getNotes()
        );
    }
}