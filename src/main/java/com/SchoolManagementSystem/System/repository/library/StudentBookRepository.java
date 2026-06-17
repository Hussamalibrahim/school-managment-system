package com.SchoolManagementSystem.System.repository.library;

import com.SchoolManagementSystem.System.entity.library.StudentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentBookRepository extends JpaRepository<StudentBook, Long>
{
}