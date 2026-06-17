package com.SchoolManagementSystem.System.repository.library;

import com.SchoolManagementSystem.System.entity.library.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long>
{
}