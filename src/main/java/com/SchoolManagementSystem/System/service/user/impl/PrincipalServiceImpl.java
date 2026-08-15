package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.dto.school.DashboardStatsDto;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.System.entity.finance.Payment;
import com.SchoolManagementSystem.System.entity.user.*;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.user.PrincipalMapper;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.System.repository.finance.PaymentRepository;
import com.SchoolManagementSystem.System.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.student.WarningRepository;
import com.SchoolManagementSystem.System.repository.user.*;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.mapper.AuthUserMapper;
import com.SchoolManagementSystem.System.service.NationalIdValidator;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import com.SchoolManagementSystem.System.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;
    private final TeacherRepository teacherRepository;
    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceRepository attendanceRepository;
    private final PaymentRepository paymentRepository;
    private final AnnouncementRepository announcementRepository;
    private final WarningRepository warningRepository;
    private final AuthUserRepository authUserRepository;
    private final com.SchoolManagementSystem.System.repository.school.SchoolRepository schoolRepository;
    private final NationalIdValidator nationalIdValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PrincipalDto save(PrincipalDto dto) {
        if (nationalIdValidator.validate(dto.nationalId()))
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);

        Principal principal = PrincipalMapper.toEntity(dto);
        return PrincipalMapper.toDto(principalRepository.save(principal));
    }

    @Override
    public PrincipalDto update(Long id, PrincipalDto dto) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRINCIPAL_NOT_FOUND));

        if ((!principal.getNationalId().equals(dto.nationalId())) && nationalIdValidator.validate(dto.nationalId())) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }

        PrincipalMapper.updateEntity(principal, dto);
        return PrincipalMapper.toDto(principalRepository.save(principal));
    }

    @Override
    @Transactional(readOnly = true)
    public PrincipalDto getById(Long id) {
        return principalRepository.findById(id)
                .map(PrincipalMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRINCIPAL_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrincipalDto> getAll() {
        return principalRepository.findAll()
                .stream()
                .map(PrincipalMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRINCIPAL_NOT_FOUND));
        principalRepository.delete(principal);
    }

    @Override
    public void createStaff(CreateUserRequest request) {
        validateEmail(request.email());

        if (nationalIdValidator.validate(request.nationalId()))
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);

        switch (request.role()) {
            case TEACHER -> createTeacher(request);
            case SECRETARY -> createSecretary(request);
            case LIBRARIAN -> createLibrarian(request);
            default -> throw new ValidationException(ErrorCode.UNSUPPORTED_ROLE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalTeachers = teacherRepository.count();
        long totalGuardians = guardianRepository.count();
        long totalClasses = schoolClassRepository.count();
        long totalSubjects = subjectRepository.count();
        long totalAnnouncements = announcementRepository.count();
        long totalWarnings = warningRepository.count();

        long presentCount = attendanceRepository.countByAttendanceStatus(AttendanceStatus.PRESENT);
        long absentCount = attendanceRepository.countByAttendanceStatus(AttendanceStatus.ABSENT);
        long lateCount = attendanceRepository.countByAttendanceStatus(AttendanceStatus.LATE);

        double totalPayments = paymentRepository.findAll()
                .stream()
                .filter(p -> p.getAmount() != null)
                .mapToDouble(Payment::getAmount)
                .sum();

        return new DashboardStatsDto(
                totalStudents,
                totalTeachers,
                totalGuardians,
                totalClasses,
                totalSubjects,
                totalAnnouncements,
                totalWarnings,
                presentCount,
                absentCount,
                lateCount,
                totalPayments
        );
    }

    private void createTeacher(CreateUserRequest request) {
        Teacher teacher = new Teacher();
        fillBaseUser(teacher, request);
        teacher.setSpecialization(request.specialization());
        teacher = teacherRepository.save(teacher);
        createAuthUser(request, teacher.getId());
    }

    private void createSecretary(CreateUserRequest request) {
        Secretary secretary = new Secretary();
        fillBaseUser(secretary, request);
        secretary = secretaryRepository.save(secretary);
        createAuthUser(request, secretary.getId());
    }

    private void createLibrarian(CreateUserRequest request) {
        Librarian librarian = new Librarian();
        fillBaseUser(librarian, request);
        librarian = librarianRepository.save(librarian);
        createAuthUser(request, librarian.getId());
    }

    private void fillBaseUser(BaseUser baseUser, CreateUserRequest request) {
        baseUser.setNationalId(request.nationalId());
        baseUser.setFirstName(request.firstName());
        baseUser.setLastName(request.lastName());
        baseUser.setPhone(request.phone());
        baseUser.setAddress(request.address());
        baseUser.setHireDate(request.hireDate());
    }

    private void createAuthUser(CreateUserRequest request, Long refId) {
        AuthUser user = AuthUserMapper.fromRegisterRequest(
                request.email(),
                passwordEncoder.encode("123456"),
                refId,
                request.role()
        );
        Long schoolId = com.SchoolManagementSystem.System.tenant.TenantContext.getSchoolId();
        if (schoolId != null) {
            schoolRepository.findById(schoolId).ifPresent(user::setSchool);
        }
        authUserRepository.save(user);
    }

    private void validateEmail(String email) {
        Long schoolId = com.SchoolManagementSystem.System.tenant.TenantContext.getSchoolId();
        if (schoolId != null) {
            if (authUserRepository.findByEmailAndSchoolId(email, schoolId).isPresent()) {
                throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
        } else {
            if (authUserRepository.findByEmail(email).isPresent()) {
                throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
        }
    }
}