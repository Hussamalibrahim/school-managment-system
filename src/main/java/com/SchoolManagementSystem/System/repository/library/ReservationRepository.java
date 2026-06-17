package com.SchoolManagementSystem.System.repository.library;

import com.SchoolManagementSystem.System.entity.library.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>
{}