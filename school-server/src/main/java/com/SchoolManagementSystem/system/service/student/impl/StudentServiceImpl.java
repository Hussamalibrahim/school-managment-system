package com.SchoolManagementSystem.system.service.student.impl;

import com.SchoolManagementSystem.system.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.entity.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.system.mapper.student.StudentMapper;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.system.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.system.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.security.AuthUserRepository;
import com.SchoolManagementSystem.system.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.system.security.mapper.AuthUserMapper;
import com.SchoolManagementSystem.system.service.student.StudentService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.SchoolManagementSystem.system.entity.academic.SchoolClass;

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
    public void save(AuthRequestStudent request) {
        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }
        if (authUserRepository.findByEmailAndSchoolId(request.email(), schoolId).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if(studentRepository.findByRegistrationNumber(request.registrationNumber()).isPresent()){

            throw new AlreadyExistsException(
                    ErrorCode.REGISTRATION_NUMBER_ALREADY_EXISTS
            );
        }
        Student student = StudentMapper.fromAuthRequestStudent(request);

        Student savedStudent = studentRepository.save(student);

        AuthUser authUser =
                AuthUserMapper.fromRegisterRequest(
                        request.email(),
                        passwordEncoder.encode("1234"),
                        savedStudent.getId(),
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

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        authUserRepository.deleteByRefIdAndRole(id, Role.STUDENT);
        studentRepository.delete(student);
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