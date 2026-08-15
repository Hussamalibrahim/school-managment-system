package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.student.StudentGuardianMapper;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.mapper.user.GuardianMapper;
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
public class StudentGuardianServiceImpl implements StudentGuardianService {


    private final StudentGuardianRepository studentGuardianRepository;
    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;


    @Override
    @Transactional
    public StudentGuardianDto save(StudentGuardianDto dto) {

        StudentGuardian relation = StudentGuardianMapper.toEntity(dto);

        return StudentGuardianMapper.toDto(studentGuardianRepository.save(relation));
    }


    @Override
    @Transactional
    public StudentGuardianDto update(Long id, StudentGuardianDto dto) {


        StudentGuardian relation = findRelation(id);


        relation.setPrimaryGuardian(dto.primaryGuardian());


        return StudentGuardianMapper.toDto(studentGuardianRepository.save(relation));
    }


    @Override
    @Transactional(readOnly = true)
    public StudentGuardianDto getById(Long id) {

        return StudentGuardianMapper.toDto(findRelation(id));
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentGuardianDto> getAll() {

        return studentGuardianRepository.findAll().stream().map(StudentGuardianMapper::toDto).toList();
    }


    @Override
    @Transactional
    public void delete(Long id) {

        studentGuardianRepository.delete(findRelation(id));
    }


    @Override
    @Transactional
    public StudentGuardianDto connectStudentToGuardian(Long studentId, Long guardianId, Boolean primaryGuardian) {

        if (studentGuardianRepository.existsByStudentIdAndGuardianId(studentId, guardianId)) {
            throw new AlreadyExistsException(ErrorCode.RELATION_ALREADY_EXISTS);
        }
        Student student = findStudent(studentId);

        Guardian guardian = findGuardian(guardianId);

        if (Boolean.TRUE.equals(primaryGuardian)) {
            removeCurrentPrimaryGuardian(studentId);
        }
        StudentGuardian relation = new StudentGuardian();
        relation.setStudent(student);
        relation.setGuardian(guardian);
        relation.setPrimaryGuardian(Boolean.TRUE.equals(primaryGuardian));

        return StudentGuardianMapper.toDto(studentGuardianRepository.save(relation));
    }

    @Override
    @Transactional
    public StudentGuardianDto changePrimaryGuardian(Long studentId, Long newGuardianId) {

        StudentGuardian newGuardianRelation = studentGuardianRepository.findByStudentIdAndGuardianId(studentId, newGuardianId).orElseThrow(() -> new NotFoundException(ErrorCode.RELATION_NOT_FOUND));

        removeCurrentPrimaryGuardian(studentId);

        newGuardianRelation.setPrimaryGuardian(true);

        return StudentGuardianMapper.toDto(studentGuardianRepository.save(newGuardianRelation));
    }


    private void removeCurrentPrimaryGuardian(Long studentId) {

        studentGuardianRepository.findByStudentId(studentId).stream().filter(StudentGuardian::getPrimaryGuardian).forEach(relation -> {
            relation.setPrimaryGuardian(false);
            studentGuardianRepository.save(relation);
        });
    }


    @Override
    @Transactional(readOnly = true)
    public List<GuardianDto> getStudentGuardians(Long studentId) {

        findStudent(studentId);

        return studentGuardianRepository.findByStudentId(studentId).stream().map(StudentGuardian::getGuardian).map(GuardianMapper::toDto).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getGuardianStudents(Long guardianId) {

        findGuardian(guardianId);

        return studentGuardianRepository.findByGuardianId(guardianId).stream().map(StudentGuardian::getStudent).map(StudentMapper::toDto).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsWithoutGuardian() {

        List<Student> students = studentGuardianRepository.findStudentsWithoutGuardian();

        if (students.isEmpty()) {

            throw new NotFoundException(ErrorCode.NO_STUDENTS_WITHOUT_GUARDIAN);
        }

        return students.stream().map(StudentMapper::toDto).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<GuardianDto> getGuardiansWithoutStudents() {

        List<Guardian> guardians = studentGuardianRepository.findGuardiansWithoutStudents();

        if (guardians.isEmpty()) {
            throw new NotFoundException(ErrorCode.NO_GUARDIANS_WITHOUT_STUDENTS);
        }

        return guardians.stream().map(GuardianMapper::toDto).toList();
    }


    private StudentGuardian findRelation(Long id) {

        return studentGuardianRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.RELATION_NOT_FOUND));
    }


    private Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));
    }


    private Guardian findGuardian(Long id) {

        return guardianRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));
    }

    @Override
    public void removeRelation(Long studentId, Long guardianId) {
        findStudent(studentId);
        findGuardian(guardianId);

        StudentGuardian studentGuardian = studentGuardianRepository.findByStudentIdAndGuardianId(studentId, guardianId).orElseThrow(() -> new NotFoundException(ErrorCode.RELATION_NOT_FOUND));

        if (studentGuardian.getPrimaryGuardian()) throw new ValidationException(ErrorCode.CANT_DELETE_PRIMARY_GUARDIAN);

        studentGuardianRepository.delete(studentGuardian);
    }

    @Override
    public boolean isStudentBelongsToGuardian(Long studentId, Long guardianId) {
        return studentGuardianRepository.existsByStudentIdAndGuardianId(studentId,guardianId);
    }
}