package com.SchoolManagementSystem.system.service.communication;

import com.SchoolManagementSystem.system.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.system.dto.communication.request.AnnouncementRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface AnnouncementService {

    AnnouncementDto create(UserPrincipal principal, AnnouncementRequest request);

    AnnouncementDto getById(Long id);

    List<AnnouncementDto> getAll();

    void delete(Long id);
}