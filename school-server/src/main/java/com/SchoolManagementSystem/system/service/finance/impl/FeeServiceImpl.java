package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.FeeStatus;
import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.entity.finance.FeeStructure;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.system.repository.finance.FeeRepository;
import com.SchoolManagementSystem.system.repository.finance.FeeStructureRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.finance.FeeService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeServiceImpl implements FeeService {
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;

    @Override
    public void createFeesForStudent(Student student) {

        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }

        AcademicYear academicYear = academicYearRepository
                        .findBySchoolIdAndCurrentYearTrue(schoolId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUND));

        Semester semester = semesterRepository.findCurrentSemester(academicYear.getId(), LocalDate.now())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        List<FeeStructure> structures = feeStructureRepository
                .findBySemesterIdAndGradeLevel(semester.getId(), student.getGradeLevel());

        if (structures.isEmpty()) {
            return;
        }

        List<Fee> fees = structures
                .stream()
                .filter(FeeStructure::getActive)
                .map(structure -> {
                    Fee fee = new Fee();
                    fee.setStudent(student);
                    fee.setFeeStructure(structure);
                    fee.setAmount(structure.getAmount());
                    fee.setDueDate(structure.getDueDate());
                    return fee;})
                .toList();

        feeRepository.saveAll(fees);
    }

    @Override
    @Transactional
    public void applyFeeStructureToStudents(FeeStructure feeStructure) {

        List<Student> students = studentRepository.findByGradeLevel(feeStructure.getGradeLevel());

        List<Fee> fees = students.stream()
                .filter(student -> !feeRepository
                        .existsByStudentIdAndFeeStructureId(student.getId(), feeStructure.getId()))
                .map(student -> {
                    Fee fee = new Fee();
                    fee.setStudent(student);
                    fee.setFeeStructure(feeStructure);
                    fee.setAmount(feeStructure.getAmount());
                    fee.setDueDate(feeStructure.getDueDate());

                    return fee;})
                .toList();

        feeRepository.saveAll(fees);
    }
}
