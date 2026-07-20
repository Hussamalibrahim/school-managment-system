package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;
import com.SchoolManagementSystem.System.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.System.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {


    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolRepository schoolRepository;

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
    public void save(AuthRequestStudent authRequestStudent) {

        if (authUserRepository.findByEmail(authRequestStudent.email()).isPresent()) {
            log.info("Student already exists");
            throw new RuntimeException("Email already exists");
        }
        if (studentRepository.findByRegistrationNumber(authRequestStudent.registrationNumber()).isPresent()) {
            log.info("Student already exists");
            throw new RuntimeException("Registration number already exists");
        }
        Student student = new Student();

        student.setRegistrationNumber(authRequestStudent.registrationNumber());
        student.setFirstName(authRequestStudent.firstName());
        student.setLastName(authRequestStudent.lastName());
        student.setPhone(authRequestStudent.phone());
        student.setGender(authRequestStudent.gender());
        student.setGradeLevel(authRequestStudent.gradeLevel());
        student.setDateOfBirth(authRequestStudent.dateOfBirth());
        student.setAddress(authRequestStudent.address());
        student.setEnrollmentDate(authRequestStudent.enrollmentDate());
        student.setNotes(authRequestStudent.notes());

        Student studentSaved = studentRepository.save(student);

        AuthUser authUser = new AuthUser();
        authUser.setEmail(authRequestStudent.email());
        authUser.setPassword(passwordEncoder.encode("1234"));
        authUser.setRole(Role.STUDENT);
        authUser.setRefId(studentSaved.getId());

        authUserRepository.save(authUser);
    }
    @Override
    public StudentDto save(StudentDto dto) {
        //TODO should remove it
        return null;
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
    public StudentDto getById(Long id) {
        return studentRepository.findById(id)
                .map(StudentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<StudentDto> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    public List<SubjectNameDto> getNamesSubjectByGradeAndSemester(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        return subjectRepository// Semester.FIRST WILL BY Schoolrepo.findById(student,getId)
                .findByGradeLevelAndSemester(student.getGradeLevel(), Semester.FIRST)
                .stream()
                .map(SubjectMapper::toNameDto)
                .toList();
    }
    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }


    @Override
    public List<StudentDto> getStudentsByClass_Id(Long id) {
        return studentRepository.findByStudentSchoolClass_Id(id)
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }
}