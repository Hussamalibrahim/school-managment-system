package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.dtoMapper.student.AttendanceMapper;
import com.SchoolManagementSystem.System.entity.student.Attendance;
import com.SchoolManagementSystem.System.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.System.service.student.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;

    @Override
    public AttendanceDto save(AttendanceDto dto) {
        Attendance attendance = AttendanceMapper.toEntity(dto);
        attendance = repository.save(attendance);
        return AttendanceMapper.toDto(attendance);
    }

    @Override
    public AttendanceDto update(Long id, AttendanceDto dto) {
        Attendance attendance = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setAttendanceDate(dto.attendanceDate());
        attendance.setStatus(dto.status());

        attendance = repository.save(attendance);
        return AttendanceMapper.toDto(attendance);
    }

    @Override
    public AttendanceDto getById(Long id) {
        return repository.findById(id)
                .map(AttendanceMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    @Override
    public List<AttendanceDto> getAll() {
        return repository.findAll()
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
