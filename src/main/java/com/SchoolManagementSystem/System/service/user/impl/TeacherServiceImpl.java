package com.SchoolManagementSystem.System.service.user.impl;


import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.mapper.user.TeacherMapper;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.NationalIdValidator;
import com.SchoolManagementSystem.System.service.user.TeacherService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final StudentRepository studentRepository;
    private final NationalIdValidator nationalIdValidator;



    @Override
    @Transactional
    public TeacherDto save(TeacherDto dto) {
        if (nationalIdValidator.validate(dto.nationalId())) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }

        Teacher teacher = TeacherMapper.toEntity(dto);

        return TeacherMapper.toDto(teacherRepository.save(teacher)
        );
    }

    @Override
    @Transactional
    public TeacherDto update(Long id, TeacherDto dto) {

        Teacher teacher =
                teacherRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        if (!teacher.getNationalId()
                .equals(dto.nationalId()) && nationalIdValidator.validate(dto.nationalId())) {

            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }
        TeacherMapper.updateEntity(teacher, dto);

        return TeacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto getById(Long id) {

        return teacherRepository.findById(id)
                .map(TeacherMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.TEACHER_NOT_FOUND
                        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherDto> getAll() {

        return teacherRepository.findAll()
                .stream()
                .map(TeacherMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Teacher teacher =
                teacherRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        teacherRepository.delete(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getMyStudents(Long teacherId) {


        if (!teacherRepository.existsById(teacherId)) {

            throw new NotFoundException(
                    ErrorCode.TEACHER_NOT_FOUND
            );
        }


        List<ClassSchedule> schedules =
                classScheduleRepository.findByTeacherId(teacherId);

        Set<Long> classIds =
                schedules.stream()
                        .map(s ->
                                s.getSchoolClass().getId()
                        )
                        .collect(Collectors.toSet());



        List<Student> students =
                studentRepository.findByStudentSchoolClass_IdIn(classIds);

        return students.stream()
                .map(StudentMapper::toDto)
                .toList();
    }
}