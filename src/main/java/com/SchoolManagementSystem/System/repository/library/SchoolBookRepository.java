package com.SchoolManagementSystem.System.repository.library;

import com.SchoolManagementSystem.System.entity.library.SchoolBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SchoolBookRepository extends JpaRepository<SchoolBook, Long>
{
}