package com.SchoolManagementSystem.System.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file, String folder);

    Resource load(String path);

    void delete(String path);

}