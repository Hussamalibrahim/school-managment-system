package com.SchoolManagementSystem.System.service.file;

import com.SchoolManagementSystem.System.dto.file.FileDto;
import com.SchoolManagementSystem.System.dto.file.request.GuardianFileUploadRequest;
import com.SchoolManagementSystem.System.dto.file.request.StudentFileUploadRequest;
import com.SchoolManagementSystem.System.dto.file.request.UserFileUploadRequest;
import com.SchoolManagementSystem.System.dto.file.response.DownloadFileResponse;
import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.entity.file.File;
import org.springframework.core.io.Resource;
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