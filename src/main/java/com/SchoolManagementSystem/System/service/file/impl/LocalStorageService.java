package com.SchoolManagementSystem.System.service.file.impl;

import com.SchoolManagementSystem.System.config.StorageProperties;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.StorageException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.service.file.StorageService;
import com.SchoolManagementSystem.System.utils.file.FileExtensionUtil;
import com.SchoolManagementSystem.System.utils.file.FileNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private final StorageProperties properties;

    @Override
    @Transactional
    public String store(MultipartFile file, String folder) {

        try {

            Path uploadFolder = Paths.get(properties.getLocation())
                    .resolve(folder);

            Files.createDirectories(uploadFolder);

            String extension = FileExtensionUtil.getExtension(
                    file.getOriginalFilename());

            String storedName =
                    FileNameUtil.generate(extension);

            Path destination =
                    uploadFolder.resolve(storedName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return destination.toString();

        } catch (IOException e) {
            throw new StorageException(ErrorCode.FILE_STORAGE_FAILED);
        }
    }

    @Override
    public Resource load(String path) {

        try {

            Resource resource =
                    new UrlResource(Paths.get(path).toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new NotFoundException(ErrorCode.FILE_NOT_FOUND);
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new StorageException(ErrorCode.FILE_LOAD_FAILED);
        }
    }

    @Override
    @Transactional
    public void delete(String path) {

        try {

            Files.deleteIfExists(Paths.get(path));

        } catch (IOException e) {
            throw new StorageException(ErrorCode.FILE_DELETE_FAILED);
        }
    }
}