package com.SchoolManagementSystem.system.service.file.impl;

import com.SchoolManagementSystem.system.dto.file.FileDto;
import com.SchoolManagementSystem.system.dto.file.request.GuardianFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.request.StudentFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.request.UserFileUploadRequest;
import com.SchoolManagementSystem.system.dto.file.response.DownloadFileResponse;
import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import com.SchoolManagementSystem.system.entity.file.File;
import com.SchoolManagementSystem.system.exception.business.AuthenticationException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.file.FileMapper;
import com.SchoolManagementSystem.system.repository.file.FileRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.user.GuardianRepository;
import com.SchoolManagementSystem.system.repository.user.LibrarianRepository;
import com.SchoolManagementSystem.system.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.system.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.file.FileService;
import com.SchoolManagementSystem.system.service.file.StorageService;
import com.SchoolManagementSystem.system.utils.file.FileExtensionUtil;
import com.SchoolManagementSystem.system.utils.file.FileFolderUtil;
import com.SchoolManagementSystem.system.utils.file.FileValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository repository;
    private final StorageService storageService;

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final TeacherRepository teacherRepository;
    private final PrincipalRepository principalRepository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;

    @Override
    @Transactional
    public FileDto uploadStudentFile(
            StudentFileUploadRequest request,
            MultipartFile multipartFile) {

        validateOwner(FileOwnerType.STUDENT, request.studentId());

        UserPrincipal currentUser = getCurrentUser();

        File file = createFile(
                multipartFile,
                request.fileType(),
                FileOwnerType.STUDENT,
                request.studentId(),
                UserType.valueOf(currentUser.getRole().name()),
                currentUser.getRefId());

        repository.save(file);

        return FileMapper.toDto(file);
    }

    @Override
    @Transactional
    public FileDto uploadGuardianFile(
            GuardianFileUploadRequest request,
            MultipartFile multipartFile) {

        validateOwner(FileOwnerType.GUARDIAN, request.guardianId());

        UserPrincipal currentUser = getCurrentUser();

        File file = createFile(
                multipartFile,
                request.fileType(),
                FileOwnerType.GUARDIAN,
                request.guardianId(),
                UserType.valueOf(currentUser.getRole().name()),
                currentUser.getRefId());

        repository.save(file);

        return FileMapper.toDto(file);
    }

    @Override
    @Transactional
    public FileDto uploadUserFile(
            UserFileUploadRequest request,
            MultipartFile multipartFile) {

        FileOwnerType ownerType =
                FileOwnerType.valueOf(request.employeeType().name());

        validateOwner(ownerType, request.employeeId());

        UserPrincipal currentUser = getCurrentUser();

        File file = createFile(
                multipartFile,
                request.fileType(),
                ownerType,
                request.employeeId(),
                UserType.valueOf(currentUser.getRole().name()),
                currentUser.getRefId());

        repository.save(file);

        return FileMapper.toDto(file);
    }

    @Override
    @Transactional(readOnly = true)
    public DownloadFileResponse download(Long id) {

        File file = findFile(id);

        Resource resource =
                storageService.load(file.getFilePath());

        return new DownloadFileResponse(
                resource,
                file.getOriginalName(),
                file.getMimeType(),
                file.getFileSize());
    }

    @Override
    @Transactional
    public void delete(Long id) {

        File file = findFile(id);

        storageService.delete(file.getFilePath());

        repository.delete(file);
    }

    @Override
    @Transactional(readOnly = true)
    public FileDto getById(Long id) {

        return FileMapper.toDto(findFile(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileDto> getByOwner(
            FileOwnerType ownerType,
            Long ownerId) {

        validateOwner(ownerType, ownerId);

        return repository.findByOwnerTypeAndOwnerId(ownerType, ownerId)
                .stream()
                .map(FileMapper::toDto)
                .toList();
    }

    private File createFile(
            MultipartFile multipartFile,
            FileType fileType,
            FileOwnerType ownerType,
            Long ownerId,
            UserType uploadedByType,
            Long uploadedById) {

        FileValidationUtil.validate(multipartFile, fileType);

        String folder =
                FileFolderUtil.getFolder(ownerType, ownerId);

        String path =
                storageService.store(multipartFile, folder);

        File file = new File();

        file.setOriginalName(multipartFile.getOriginalFilename());

        file.setStoredName(
                Paths.get(path)
                        .getFileName()
                        .toString());

        file.setFilePath(path);

        file.setMimeType(multipartFile.getContentType());

        file.setFileSize(multipartFile.getSize());

        file.setExtension(
                FileExtensionUtil.getExtension(
                        multipartFile.getOriginalFilename()));

        file.setFileType(fileType);

        file.setOwnerType(ownerType);
        file.setOwnerId(ownerId);

        file.setUploadedByType(uploadedByType);
        file.setUploadedById(uploadedById);

        return file;
    }

    private void validateOwner(FileOwnerType ownerType, Long ownerId) {

        boolean exists = switch (ownerType) {

            case STUDENT -> studentRepository.existsById(ownerId);

            case GUARDIAN -> guardianRepository.existsById(ownerId);

            case TEACHER -> teacherRepository.existsById(ownerId);

            case PRINCIPAL -> principalRepository.existsById(ownerId);

            case SECRETARY -> secretaryRepository.existsById(ownerId);

            case LIBRARIAN -> librarianRepository.existsById(ownerId);

            default -> throw new ValidationException(
                    ErrorCode.INVALID_OWNER_TYPE);
        };

        if (!exists) {
            throw new ValidationException(ErrorCode.OWNER_NOT_FOUND);
        }
    }

    private File findFile(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.FILE_NOT_FOUND));
    }

    private UserPrincipal getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new AuthenticationException(ErrorCode.UNAUTHORIZED);
        }

        return userPrincipal;
    }
}