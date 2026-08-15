package com.SchoolManagementSystem.system.repository.file;

import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.system.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByOwnerTypeAndOwnerId(FileOwnerType ownerType, Long ownerId);
}