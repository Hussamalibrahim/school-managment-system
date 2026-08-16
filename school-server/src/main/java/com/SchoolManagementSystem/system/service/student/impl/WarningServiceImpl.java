package com.SchoolManagementSystem.system.service.student.impl;

import com.SchoolManagementSystem.system.dto.student.respones.GuardianStudentWarningsDto;
import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.dto.student.request.CreateWarningDto;
import com.SchoolManagementSystem.system.dto.student.respones.WarningStatisticsDto;
import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.student.WarningMapper;
import com.SchoolManagementSystem.system.entity.student.Warning;
import com.SchoolManagementSystem.system.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.student.WarningRepository;
import com.SchoolManagementSystem.system.service.student.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WarningServiceImpl implements WarningService {

    private final WarningRepository warningRepository;
    private final StudentRepository studentRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final StudentGuardianRepository studentGuardianRepository;

    @Override
    public WarningDto save(WarningDto dto) {
        Warning warning = WarningMapper.toEntity(dto);
        warning = warningRepository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto update(Long id, WarningDto dto) {
        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warning not found"));

        warning.setWarningDate(dto.warningDate());
        warning.setReason(dto.reason());

        warning = warningRepository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto getById(Long id) {
        return warningRepository.findById(id)
                .map(WarningMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Warning not found"));
    }

    @Override
    public List<WarningDto> getAll() {
        return warningRepository.findAll()
                .stream()
                .map(WarningMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        warningRepository.deleteById(id);
    }

    @Transactional
    public WarningDto create(CreateWarningDto dto, Long teacherId) {

        Student student = studentRepository
                .findById(dto.studentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        if (student.getStudentSchoolClass() == null) {
            throw new ValidationException(ErrorCode.STUDENT_HAS_NO_CLASS);
        }

        Long classId = student.getStudentSchoolClass().getId();

        if (!classScheduleRepository.existsByTeacherIdAndSchoolClassId(teacherId, classId)) {
            throw new ValidationException(ErrorCode.YOU_DONT_TEACH_THIS_CLASS);
        }

        Warning warning = new Warning();

        warning.setStudent(student);
        warning.setReason(dto.reason());
        warning.setMessage(dto.message());
        warning.setWarningDate(LocalDate.now());

        return WarningMapper.toDto(warningRepository.save(warning));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuardianStudentWarningsDto> getGuardianChildrenWarnings(
            Long guardianId) {

        return studentGuardianRepository
                .findByGuardianId(guardianId)
                .stream()
                .map(StudentGuardian::getStudent)
                .map(student -> {
                    List<WarningDto> warnings =
                            warningRepository
                                    .findByStudentId(student.getId())
                                    .stream()
                                    .map(WarningMapper::toDto)
                                    .toList();

                    return new GuardianStudentWarningsDto(
                            student.getId(),
                            student.getFirstName() + " " + student.getLastName(),
                            warnings);}).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarningStatisticsDto> getWarningStatistics(WarningReason reason, Long count) {

        List<Student> students;

        if (count != null) {
            students = studentRepository.findStudentsByWarningStatistics(reason, count);
        } else {
            students = studentRepository.findStudentsByWarningReason(reason);
        }

        return students.stream()
                .map(student -> {
                    List<WarningDto> warnings =
                            warningRepository
                                    .findByStudentId(student.getId())
                                    .stream()
                                    .map(WarningMapper::toDto)
                                    .toList();

                    return new WarningStatisticsDto(
                            student.getId(), student.getFirstName() + " " + student.getLastName(), warnings);
                })
                .toList();
    }
}
