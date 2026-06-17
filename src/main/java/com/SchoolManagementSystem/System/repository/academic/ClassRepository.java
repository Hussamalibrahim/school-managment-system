package com.SchoolManagementSystem.System.repository.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.System.entity.academic.Class;


@Repository
public interface ClassRepository extends JpaRepository<Class, Long>
{
}