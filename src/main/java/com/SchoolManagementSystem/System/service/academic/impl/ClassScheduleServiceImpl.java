package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dtoMapper.academic.ClassScheduleMapper;
import com.SchoolManagementSystem.System.entity.academic.Class;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.repository.academic.ClassRepository;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl
        implements ClassScheduleService
{
    private final ClassScheduleRepository repository;

    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ClassScheduleDto save(ClassScheduleDto dto)
    {
        Class studentClass = classRepository.findById(dto.classId()).orElseThrow();
        Subject subject = subjectRepository.findById(dto.subjectId()).orElseThrow();
        Teacher teacher = teacherRepository.findById(dto.teacherId()).orElseThrow();

        ClassSchedule schedule =
                ClassScheduleMapper.toEntity(
                        dto,
                        studentClass,
                        subject,
                        teacher
                );

        schedule = repository.save(schedule);

        return ClassScheduleMapper.toDto(schedule);
    }

    @Override
    public ClassScheduleDto update(Long id, ClassScheduleDto dto)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassScheduleDto getById(Long id)
    {
        return repository.findById(id)
                .map(ClassScheduleMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<ClassScheduleDto> getAll()
    {
        return repository.findAll()
                .stream()
                .map(ClassScheduleMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}