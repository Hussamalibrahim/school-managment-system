package com.SchoolManagementSystem.system.service.file;

import com.SchoolManagementSystem.system.dto.file.FileDto;
import com.SchoolManagementSystem.system.dto.file.request.GuardianFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.request.StudentFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.request.UserFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.response.DownloadFileResponse;
import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileDto uploadStudentFile(
            StudentFileUploadRequest request,
            MultipartFile file
    );

    FileDto uploadGuardianFile(
            GuardianFileUploadRequest request,
            MultipartFile file
    );

    FileDto uploadUserFile(
            UserFileUploadRequest request,
            MultipartFile file
    );

    DownloadFileResponse download(Long id);

    void delete(Long id);

    FileDto getById(Long id);

    List<FileDto> getByOwner(
            FileOwnerType ownerType,
            Long ownerId
    );

}