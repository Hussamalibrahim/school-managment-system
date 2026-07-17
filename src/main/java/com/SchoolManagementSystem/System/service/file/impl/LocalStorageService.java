package com.SchoolManagementSystem.System.service.file.impl;

import com.SchoolManagementSystem.System.config.StorageProperties;
import com.SchoolManagementSystem.System.service.file.StorageService;
import com.SchoolManagementSystem.System.utils.file.FileNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
    public String store(MultipartFile file, String folder) {

        try {

            Path root = Paths.get(properties.getLocation());

            Path uploadFolder = root.resolve(folder);

            Files.createDirectories(uploadFolder);

            String extension = "";

            String original = file.getOriginalFilename();

            if (original != null && original.contains(".")) {
                extension = original.substring(original.lastIndexOf('.') + 1);
            }

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

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String path) {

        try {

            Path file = Paths.get(path);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("File not found.");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(String path) {

        try {

            Files.deleteIfExists(Paths.get(path));

        } catch (IOException e) {

            throw new RuntimeException("Could not delete file.", e);

        }

    }

}