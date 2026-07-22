package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.entity.academic.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TeacherSubjectRepository
        extends JpaRepository<TeacherSubject, Long>
{
    boolean existsByTeacherIdAndSubjectId(Long teacherId, Long subjectId);

    List<TeacherSubject> findByTeacher_Id(Long teacherId);

    List<TeacherSubject> findBySubject_Id(Long subjectId);
}