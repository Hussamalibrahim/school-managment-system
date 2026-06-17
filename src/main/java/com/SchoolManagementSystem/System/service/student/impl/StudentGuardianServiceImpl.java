package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.dtoMapper.student.StudentGuardianMapper;
import com.SchoolManagementSystem.System.dtoMapper.student.StudentMapper;
import com.SchoolManagementSystem.System.dtoMapper.user.GuardianMapper;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.GuardianRepository;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentGuardianServiceImpl implements StudentGuardianService {

    private final StudentGuardianRepository studentGuardianRepository;
    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;


    @Override
    public StudentGuardianDto save(StudentGuardianDto dto) {
        StudentGuardian guardian = StudentGuardianMapper.toEntity(dto);
        guardian = studentGuardianRepository.save(guardian);
        return StudentGuardianMapper.toDto(guardian);
    }

    @Override
    public StudentGuardianDto update(Long id, StudentGuardianDto dto) {
        StudentGuardian guardian = studentGuardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentGuardian not found"));

        guardian.setPrimaryGuardian(dto.primaryGuardian());

        guardian = studentGuardianRepository.save(guardian);
        return StudentGuardianMapper.toDto(guardian);
    }

    @Override
    public StudentGuardianDto getById(Long id) {
        return studentGuardianRepository.findById(id)
                .map(StudentGuardianMapper::toDto)
                .orElseThrow(() -> new RuntimeException("StudentGuardian not found"));
    }

    @Override
    public List<StudentGuardianDto> getAll() {
        return studentGuardianRepository.findAll()
                .stream()
                .map(StudentGuardianMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        studentGuardianRepository.deleteById(id);
    }

    public StudentGuardianDto connectStudentToGuardian(Long studentId, Long guardianId,Boolean primaryGuardian)
    {
        if (studentGuardianRepository.existsByStudentIdAndGuardianId(studentId, guardianId))
        {
            throw new RuntimeException("Student already linked to this guardian");
        }
        Student student = studentRepository.findById(studentId).orElseThrow();
        Guardian guardian = guardianRepository.findById(guardianId).orElseThrow();

        StudentGuardian sg = new StudentGuardian();
        sg.setStudent(student);
        sg.setGuardian(guardian);
        sg.setPrimaryGuardian(primaryGuardian);

        sg = studentGuardianRepository.save(sg);

        return StudentGuardianMapper.toDto(sg);
    }

    @Override
    public List<GuardianDto> getGuardiansByStudent(Long studentId) {

        return studentGuardianRepository.findByStudentId(studentId)
                .stream()
                .map(StudentGuardian::getGuardian)
                .map(GuardianMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDto> getStudentsByGuardian(Long guardianId) {

        return studentGuardianRepository.findByGuardianId(guardianId)
                .stream()
                .map(StudentGuardian::getStudent)
                .map(StudentMapper::toDto)
                .toList();
    }
}
