package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.Exam;
import com.SchoolManagementSystem.System.entity.enumeration.ExamCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    boolean existsBySchoolClassIdAndSubjectIdAndSemesterIdAndCategory(Long id, Long id1, Long id2, @NotNull ExamCategory category);

    boolean existsBySchoolClassIdAndSubjectIdAndSemesterIdAndCategoryAndIdNot(
            Long schoolClassId, Long subjectId, Long semesterId, ExamCategory category, Long id);

    List<Exam> findBySchoolClassIdAndSemesterIdAndIdNot(Long id, Long id1, Long examId);

    List<Exam> findBySchoolClassIdAndSemesterId(Long schoolClassId, Long semesterId);
    List<Exam> findBySubjectIdAndSemesterId(Long subjectId, Long semesterId);

    List<Exam> findBySchoolClassIdAndSubjectTeacherSubjectsTeacherId(Long id, Long id1);

    boolean existsByIdAndSubjectTeacherSubjectsTeacherId(Long examId, Long teacherId);

    boolean existsByIdAndSchoolClassIdAndSubjectTeacherSubjectsTeacherId(Long id, Long id1, Long refId);
}
