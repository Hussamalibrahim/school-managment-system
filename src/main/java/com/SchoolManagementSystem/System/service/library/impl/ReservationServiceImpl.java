package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.ReservationDto;
import com.SchoolManagementSystem.System.dtoMapper.library.ReservationMapper;
import com.SchoolManagementSystem.System.entity.library.Reservation;
import com.SchoolManagementSystem.System.repository.library.ReservationRepository;
import com.SchoolManagementSystem.System.service.library.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    @Override
    public ReservationDto save(ReservationDto dto) {
        Reservation reservation = ReservationMapper.toEntity(dto);
        reservation = repository.save(reservation);
        return ReservationMapper.toDto(reservation);
    }

    @Override
    public ReservationDto update(Long id, ReservationDto dto) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setReservationDate(dto.reservationDate());
        reservation.setStatus(dto.status());

        reservation = repository.save(reservation);
        return ReservationMapper.toDto(reservation);
    }

    @Override
    public ReservationDto getById(Long id) {
        return repository.findById(id)
                .map(ReservationMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Override
    public List<ReservationDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ReservationMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
