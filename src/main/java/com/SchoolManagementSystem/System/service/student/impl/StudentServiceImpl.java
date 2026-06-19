package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Override
    public StudentDto assignClass(Long studentId, Long classId)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        SchoolClass studentSchoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        student.setStudentSchoolClass(studentSchoolClass);

        student = studentRepository.save(student);

        return StudentMapper.toDto(student);
    }

    @Override
    public StudentDto save(StudentDto dto) {
        Student student = StudentMapper.toEntity(dto);
        student = studentRepository.save(student);
        return StudentMapper.toDto(student);
    }

    @Override
    public StudentDto update(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setRegistrationNumber(dto.registrationNumber());
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setGender(dto.gender());
        student.setDateOfBirth(dto.dateOfBirth());

        return null;
    }

    @Override
    public StudentDto getById(Long aLong) {
        return null;
    }

    @Override
    public List<StudentDto> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}