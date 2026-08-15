package com.SchoolManagementSystem.system.repository.school;

import com.SchoolManagementSystem.system.entity.school.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findByCode(String code);

    boolean existsByCode(String code);
    void deleteById(School school);

    boolean existsByName(String s);
}