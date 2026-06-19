package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.mapper.user.TeacherMapper;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final StudentRepository studentRepository;

    @Override
    public TeacherDto save(TeacherDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);

        if (teacherRepository.existsByNationalId(dto.nationalId())) {
            throw new RuntimeException("National ID already exists");
        }

        teacher = teacherRepository.save(teacher);
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public TeacherDto update(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setNationalId(dto.nationalId());
        teacher.setFirstName(dto.firstName());
        teacher.setLastName(dto.lastName());
        teacher.setPhone(dto.phone());
        teacher.setEmail(dto.email());
        teacher.setAddress(dto.address());
        teacher.setStatus(dto.status());
        teacher.setHireDate(dto.hireDate());
        teacher.setSpecialization(dto.specialization());

        teacher = teacherRepository.save(teacher);
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public TeacherDto getById(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public List<TeacherDto> getAll() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean existsByNationalId(String nationalId) {
        return teacherRepository.existsByNationalId(nationalId);
    }
    @Override
    public List<StudentDto> getMyStudents(Long teacherId) {

        List<ClassSchedule> schedules =
                classScheduleRepository.findByTeacherId(teacherId);

        Set<Long> classIds = schedules.stream()
                .map(s -> s.getSchoolClass().getId())
                .collect(Collectors.toSet());

        List<Student> students =
                studentRepository.findByStudentSchoolClassIdIn(
                        new ArrayList<>(classIds)
                );

        return students.stream()
                .map(StudentMapper::toDto)
                .toList();
    }
}
