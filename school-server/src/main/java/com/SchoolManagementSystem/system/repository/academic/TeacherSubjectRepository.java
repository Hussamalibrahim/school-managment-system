package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherSubjectRepository
        extends JpaRepository<TeacherSubject, Long>
{
    boolean existsByTeacherIdAndSubjectId(Long teacherId, Long subjectId);

    List<TeacherSubject> findByTeacher_Id(Long teacherId);

    List<TeacherSubject> findBySubject_Id(Long subjectId);

}