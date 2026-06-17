package com.SchoolManagementSystem.System.repository.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.System.entity.library.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>
{
}