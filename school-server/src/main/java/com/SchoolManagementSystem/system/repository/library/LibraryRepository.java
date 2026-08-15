package com.SchoolManagementSystem.system.repository.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.system.entity.library.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>
{
}