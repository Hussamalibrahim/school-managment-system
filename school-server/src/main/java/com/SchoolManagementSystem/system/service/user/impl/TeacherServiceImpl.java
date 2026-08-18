package com.SchoolManagementSystem.system.service.user.impl;


import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.user.TeacherDto;
import com.SchoolManagementSystem.system.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.student.StudentMapper;
import com.SchoolManagementSystem.system.mapper.user.TeacherMapper;
import com.SchoolManagementSystem.system.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.service.NationalIdValidator;
import com.SchoolManagementSystem.system.service.user.TeacherService;

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
    private final AuthUserRepository authUserRepository;
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
        Teacher teacher = teacherRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        authUserRepository.deleteByRefIdAndRole(id, Role.TEACHER);

        teacherRepository.delete(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getMyStudents(Long teacherId) {


        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
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