package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.System.security.mapper.AuthUserMapper;
import com.SchoolManagementSystem.System.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.SchoolManagementSystem.System.entity.academic.SchoolClass;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {


    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ClassScheduleRepository classScheduleRepo;

    @Override
    @Transactional
    public StudentDto assignClass(Long studentId, Long classId)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        SchoolClass studentSchoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        student.setStudentSchoolClass(studentSchoolClass);

        return StudentMapper.toDto(studentRepository.save(student));
    }
    @Override
    @Transactional
    public void save(AuthRequestStudent authRequestStudent) {

        if (authUserRepository.findByEmail(authRequestStudent.email()).isPresent()) {
            throw new NotFoundException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (studentRepository.findByRegistrationNumber(authRequestStudent.registrationNumber()).isPresent()) {
            throw new NotFoundException(ErrorCode.REGISTRATION_NUMBER_ALREADY_EXISTS);
        }

        Student studentSaved = studentRepository.save(
                StudentMapper.fromAuthRequestStudent(authRequestStudent));

        AuthUser authUser = AuthUserMapper.fromRegisterRequest(authRequestStudent.email(),
                passwordEncoder.encode("1234"),
                studentSaved.getId(),
                Role.STUDENT);

        authUserRepository.save(authUser);
    }

    @Override
    public StudentDto save(StudentDto dto) {
        //TODO should remove it
        return null;
    }

    @Override
    @Transactional
    public StudentDto update(Long id, StudentDto dto) {
        Student student =
                studentRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        if (studentRepository.existsByRegistrationNumberAndIdNot(dto.registrationNumber(), id)) {
            throw new AlreadyExistsException(ErrorCode.REGISTRATION_NUMBER_ALREADY_EXISTS);
        }

        StudentMapper.updateEntity(student,dto);

        return StudentMapper.toDto(
                studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getById(Long id) {
        return studentRepository.findById(id)
                .map(StudentMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectNameDto> getNamesSubjectByGradeAndSemester(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        return subjectRepository// SemesterName.FIRST WILL BY Schoolrepo.findById(student,getId)
                .findByGradeLevelAndSemesterName(student.getGradeLevel(), SemesterName.FIRST)
                .stream()
                .map(SubjectMapper::toNameDto)
                .toList();
    }
    @Override
    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByClass_Id(Long id) {
        return studentRepository.findByStudentSchoolClass_Id(id)
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsTeacherByClassId(Long classId, Long teacherId) {

        if (!classScheduleRepo.existsByTeacherIdAndSchoolClassId(teacherId, classId)) {
            throw new ValidationException(ErrorCode.YOU_DONT_TEACH_THIS_CLASS);
        }

        return studentRepository.findByStudentSchoolClass_Id(classId)
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }
}