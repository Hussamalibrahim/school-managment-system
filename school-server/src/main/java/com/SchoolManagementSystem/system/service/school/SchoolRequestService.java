package com.SchoolManagementSystem.system.service.school;

import com.SchoolManagementSystem.system.dto.school.SchoolRequestDto;

import java.util.List;

public interface SchoolRequestService {
    List<SchoolRequestDto> getAll();

    List<SchoolRequestDto> getPending();

    SchoolRequestDto getById(Long id);

    void approve(Long id);

    void reject(Long id, String reason);

}
