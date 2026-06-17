package com.SchoolManagementSystem.System.dto.library;

import com.SchoolManagementSystem.System.entity.library.Reservation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Reservation}
 */
public record ReservationDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                             LocalDate reservationDate, String status) implements Serializable {
}