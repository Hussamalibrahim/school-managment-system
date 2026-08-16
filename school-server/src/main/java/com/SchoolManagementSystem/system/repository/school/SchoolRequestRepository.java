package com.SchoolManagementSystem.system.repository.school;

import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import com.SchoolManagementSystem.system.entity.school.SchoolRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SchoolRequestRepository extends JpaRepository<SchoolRequest, Long> {

    List<SchoolRequest> findByStatus(SchoolRequestStatus status);

    long countByStatus(SchoolRequestStatus status);
}
