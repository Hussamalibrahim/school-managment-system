package com.SchoolManagementSystem.system.repository.school;

import com.SchoolManagementSystem.system.entity.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findByCode(String code);

    boolean existsByCode(String code);
    void deleteById(School school);

    boolean existsByName(String s);
    long countByEnabledTrue();

    long countByEnabledFalse();
}