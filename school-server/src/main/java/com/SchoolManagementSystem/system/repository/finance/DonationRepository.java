package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.finance.Donation;
import com.SchoolManagementSystem.system.entity.enumeration.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByCampaignIdOrderByDonationDateDesc(Long campaignId);

    List<Donation> findByCampaignIdAndStatus(Long campaignId, DonationStatus status);

    List<Donation> findByGuardianIdOrderByDonationDateDesc(Long guardianId);

    List<Donation> findByGuardianIdAndStatus(Long guardianId, DonationStatus status);

    boolean existsByReceiptNumber(String receiptNumber);
}