package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.finance.DonationCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationCampaignRepository extends JpaRepository<DonationCampaign, Long> {

    List<DonationCampaign> findByActiveTrueOrderByCreatedAtDesc();
}
