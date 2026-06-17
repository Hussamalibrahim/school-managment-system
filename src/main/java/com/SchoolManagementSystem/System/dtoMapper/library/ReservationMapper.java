package com.SchoolManagementSystem.System.dtoMapper.library;

import com.SchoolManagementSystem.System.dto.library.ReservationDto;
import com.SchoolManagementSystem.System.entity.library.Reservation;

public final class ReservationMapper {

    private ReservationMapper() {}

    public static ReservationDto toDto(Reservation reservation) {
        if (reservation == null) return null;

        return new ReservationDto(
                reservation.getId(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt(),
                reservation.getDeletedAt(),
                reservation.getReservationDate(),
                reservation.getStatus()
        );
    }

    public static Reservation toEntity(ReservationDto dto) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();

        reservation.setId(dto.id());
        reservation.setCreatedAt(dto.createdAt());
        reservation.setUpdatedAt(dto.updatedAt());
        reservation.setDeletedAt(dto.deletedAt());
        reservation.setReservationDate(dto.reservationDate());
        reservation.setStatus(dto.status());

        return reservation;
    }
}