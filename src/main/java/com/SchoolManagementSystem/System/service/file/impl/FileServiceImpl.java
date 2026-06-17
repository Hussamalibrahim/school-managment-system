package com.SchoolManagementSystem.System.service.file.impl;

import com.SchoolManagementSystem.System.dto.file.FileDto;
import com.SchoolManagementSystem.System.dtoMapper.file.FileMapper;
import com.SchoolManagementSystem.System.entity.file.File;
import com.SchoolManagementSystem.System.repository.file.FileRepository;
import com.SchoolManagementSystem.System.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository repository;

    @Override
    public FileDto save(FileDto dto) {
        File file = FileMapper.toEntity(dto);
        file = repository.save(file);
        return FileMapper.toDto(file);
    }

    @Override
    public FileDto update(Long id, FileDto dto) {
        File file = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        file.setFileName(dto.fileName());
        file.setFilePath(dto.filePath());
        file.setFileType(dto.fileType());
        file.setOwnerType(dto.ownerType());
        file.setOwnerId(dto.ownerId());
        file.setUploadedByType(dto.uploadedByType());
        file.setUploadedById(dto.uploadedById());

        file = repository.save(file);
        return FileMapper.toDto(file);
    }

    @Override
    public FileDto getById(Long id) {
        return repository.findById(id)
                .map(FileMapper::toDto)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public List<FileDto> getAll() {
        return repository.findAll()
                .stream()
                .map(FileMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
