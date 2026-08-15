package com.SchoolManagementSystem.system.service.student.impl;

import com.SchoolManagementSystem.system.config.AttendanceStatistics;
import com.SchoolManagementSystem.system.dto.student.AttendanceDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.system.dto.student.request.AttendanceRequest;
import com.SchoolManagementSystem.system.dto.student.request.StudentAttendanceRequest;
import com.SchoolManagementSystem.system.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.system.entity.student.Attendance;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.student.AttendanceMapper;
import com.SchoolManagementSystem.system.mapper.student.StudentMapper;
import com.SchoolManagementSystem.system.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.user.GuardianRepository;
import com.SchoolManagementSystem.system.service.student.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final StudentGuardianRepository studentGuardianRepository;

    @Override
    public AttendanceDto save(AttendanceCreateRequest request) {

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        if (request.attendanceDate().getDayOfWeek() == DayOfWeek.FRIDAY
                || request.attendanceDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
            throw new ValidationException(ErrorCode.CANT_ADD_PERIOD_IN_HOLIDAY);
        }

        if (attendanceRepository.existsByStudentIdAndAttendanceDate(
                request.studentId(),
                request.attendanceDate())) {

            throw new AlreadyExistsException(ErrorCode.ATTENDANCE_ALREADY_EXISTS);
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setAttendanceStatus(request.attendanceStatus());

        return AttendanceMapper.toDto(
                attendanceRepository.save(attendance)
        );
    }

    @Override
    public AttendanceDto update(Long id, AttendanceCreateRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ATTENDANCE_NOT_FOUND));

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.attendanceDate().getDayOfWeek() == DayOfWeek.FRIDAY
                || request.attendanceDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
            throw new ValidationException(ErrorCode.CANT_ADD_PERIOD_IN_HOLIDAY);
        }
        Attendance existing = attendanceRepository
                .findByStudentIdAndAttendanceDate(
                        request.studentId(),
                        request.attendanceDate());

        if (existing != null && !existing.getId().equals(id)) {
            throw new AlreadyExistsException(ErrorCode.ATTENDANCE_ALREADY_EXISTS);
        }

        attendance.setStudent(student);
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setAttendanceStatus(request.attendanceStatus());

        return AttendanceMapper.toDto(attendanceRepository.save(attendance));
    }

    @Override
    public void delete(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ATTENDANCE_NOT_FOUND));

        attendanceRepository.delete(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDto getById(Long id) {

        return AttendanceMapper.toDto(
                attendanceRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.ATTENDANCE_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAll() {

        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getStudentAttendance(Long studentId) {

        return attendanceRepository
                .findByStudentIdOrderByAttendanceDateDesc(studentId)
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getMyAttendance(Long studentId) {

        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(6);

        return attendanceRepository
                .findByStudentIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
                        studentId,
                        from,
                        today
                )
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }

    @Override
    public void saveAttendance(AttendanceRequest request) {

        if (request.attendanceDate().getDayOfWeek() == DayOfWeek.FRIDAY
                || request.attendanceDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
            throw new ValidationException(ErrorCode.CANT_ADD_PERIOD_IN_HOLIDAY);
        }

        for (StudentAttendanceRequest studentRequest : request.students()) {

            Student student = studentRepository.findById(studentRequest.studentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

            if (attendanceRepository.existsByStudentIdAndAttendanceDate(
                    student.getId(), request.attendanceDate())) {
                throw new AlreadyExistsException(ErrorCode.ATTENDANCE_ALREADY_EXISTS);
            }

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setAttendanceDate(request.attendanceDate());
            attendance.setAttendanceStatus(studentRequest.attendanceStatus());

            attendanceRepository.save(attendance);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public AttendanceStatistics getAttendanceStatistics(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }

        long absences = attendanceRepository.countByStudentIdAndAttendanceStatus(
                studentId,
                AttendanceStatus.ABSENT
        );

        long late = attendanceRepository.countByStudentIdAndAttendanceStatus(studentId, AttendanceStatus.LATE);

        long excused = attendanceRepository.countByStudentIdAndAttendanceStatus(studentId, AttendanceStatus.EXCUSED);

        return new AttendanceStatistics(absences, late, excused);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getGuardianStudentAttendance(
            Long guardianId,
            Long studentId) {

        if (!guardianRepository.existsById(guardianId)) {
            throw new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND);
        }

        if (!studentGuardianRepository.existsByStudentIdAndGuardianId(studentId, guardianId)) {
            throw new ValidationException(ErrorCode.RELATION_NOT_FOUND);
        }

        return attendanceRepository
                .findByStudentIdOrderByAttendanceDateDesc(studentId)
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getGuardianStudentsAttendance(Long guardianId) {

        if (!guardianRepository.existsById(guardianId)) {
            throw new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND);
        }

        List<Long> studentIds = studentGuardianRepository.findByGuardianId(guardianId)
                .stream()
                .map(StudentGuardian::getStudent)
                .map(Student::getId)
                .toList();

        if (studentIds.isEmpty()) {
            return List.of();
        }

        return attendanceRepository
                .findByStudentIdInOrderByAttendanceDateDesc(studentIds)
                .stream()
                .map(AttendanceMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsExceededAttendanceStatus(
            AttendanceStatus status,
            long limit) {

        return attendanceRepository.findStudentsExceeded(status, limit)
                .stream()
                .map(StudentMapper::toDto)
                .toList();
    }
}