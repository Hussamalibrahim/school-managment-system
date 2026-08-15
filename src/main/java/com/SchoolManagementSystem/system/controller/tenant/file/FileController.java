package com.SchoolManagementSystem.System.controller.tenant.file;

import com.SchoolManagementSystem.System.dto.file.FileDto;
import com.SchoolManagementSystem.System.dto.file.request.GuardianFileUploadRequest;
import com.SchoolManagementSystem.System.dto.file.request.StudentFileUploadRequest;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import com.SchoolManagementSystem.System.dto.file.request.UserFileUploadRequest;
import com.SchoolManagementSystem.System.dto.file.response.DownloadFileResponse;
import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.file.FileService;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final StudentGuardianService studentGuardianService;
    private final FileService fileService;

    @PostMapping(value = "/upload/student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadStudentFile(
            @RequestPart("request") @Valid StudentFileUploadRequest request,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadStudentFile(request, file));
    }

    @PostMapping(value = "/upload/guardian", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadGuardianFile(
            @RequestPart("request") @Valid GuardianFileUploadRequest request,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(fileService.uploadGuardianFile(request, file));
    }

    @PostMapping(value = "/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadUserFile(
            @RequestPart("request") @Valid UserFileUploadRequest request,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadUserFile(request, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getById(@AuthenticationPrincipal UserPrincipal user,@PathVariable Long id) {
        checkAuth(user,id);
        return ResponseEntity.ok(fileService.getById(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long id) {

        DownloadFileResponse response =
                fileService.download(id);

        checkAuth(user, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        response.mimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                response.originalName() + "\"")
                .contentLength(response.fileSize())
                .body(response.resource());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<FileDto>> getByOwner(
            @RequestParam FileOwnerType ownerType,
            @RequestParam Long ownerId) {

        return ResponseEntity.ok(fileService.getByOwner(ownerType, ownerId));
    }

    private void checkAuth(UserPrincipal user, Long fileId) {

        Long id = fileService.getById(fileId).ownerId();

        if (user.hasRole("SECRETARY")) {
            return;
        }

        if (id.equals(user.getRefId())) {
            return;
        }

        boolean haveSon = studentGuardianService
                .getGuardianStudents(user.getRefId())
                .stream()
                .anyMatch(student -> student.id().equals(id));

        if (haveSon) {
            return;
        }
        throw new ValidationException(ErrorCode.UNAUTHENTICATED);
    }
}