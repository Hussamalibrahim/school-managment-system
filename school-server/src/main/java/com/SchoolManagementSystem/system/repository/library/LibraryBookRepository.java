package com.SchoolManagementSystem.system.repository.library;

import com.SchoolManagementSystem.system.entity.library.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long>
{
}