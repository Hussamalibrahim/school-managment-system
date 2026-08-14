package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.ClassScheduleMapper;
import com.SchoolManagementSystem.System.mapper.student.StudentMapper;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.academic.TeacherSubjectRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.SchoolManagementSystem.System.config.ScheduleConstants.MAX_PERIODS;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository classSchedulesRepo;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ClassScheduleDto> getAll() {
        return classSchedulesRepo.findAll().
                stream().map(ClassScheduleMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassScheduleDto> getByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }
        return classSchedulesRepo.findByTeacherId(teacherId)
                .stream()
                .map(ClassScheduleMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassScheduleDto> getBySchoolClass(Long classId) {
        if (!schoolClassRepository.existsById(classId)) {
            throw new NotFoundException(ErrorCode.CLASS_NOT_FOUND);
        }

        return classSchedulesRepo.findClassScheduleBySchoolClass_Id(classId)
                .stream()
                .map(ClassScheduleMapper::toDto).toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }
        List<ClassSchedule> schedules =
                classSchedulesRepo.findByTeacherId(teacherId);

        Set<Long> classIds = schedules.stream()
                .map(s -> s.getSchoolClass().getId())
                .collect(Collectors.toSet());

        List<Student> students =
                studentRepository.findByStudentSchoolClass_IdIn(classIds);

        return students.stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ClassScheduleDto> addExtraPeriod(Long id, DayOfWeek day) {

        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
            throw new ValidationException(ErrorCode.CANT_ADD_PERIOD_IN_HOLIDAY);
        }

        List<ClassSchedule> classScheduleList = classSchedulesRepo.findClassScheduleBySchoolClass_Id(id);

        long count = classScheduleList.stream()
                .filter(s -> s.getDayOfWeek() == day)
                .count();

        if (count >= MAX_PERIODS) {
            throw new ValidationException(ErrorCode.CLASS_ALREADY_HAVE_EXTRA_PERIOD);
        }
        ClassSchedule schedule = new ClassSchedule();
        schedule.setSchoolClass(schoolClass);

        schedule.setPeriodNumber(
                PeriodNumber.values()[(int) count]
        );
        classSchedulesRepo.save(schedule);
        classScheduleList.add(schedule);

        return classScheduleList.stream()
                .map(ClassScheduleMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ClassScheduleDto assignTeacher(Long scheduleId, Long teacherId, Long subjectId) {

        ClassSchedule schedule =
                classSchedulesRepo.findById(scheduleId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_SCHEDULE_NOT_FOUND));

        Teacher teacher =
                teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        if (!teacherSubjectRepository.existsByTeacherIdAndSubjectId(teacherId, subjectId)) {
            throw new ValidationException(
                    ErrorCode.TEACHER_DOES_NOT_TEACH_SUBJECT
            );
        }
        if (classSchedulesRepo.existsByTeacherIdAndDayOfWeekAndPeriodNumberAndIdNot(
                teacherId,
                schedule.getDayOfWeek(),
                schedule.getPeriodNumber(),
                schedule.getId())) {

            throw new ValidationException(ErrorCode.TEACHER_ALREADY_ASSIGNED_AT_THIS_TIME);
        }

        if (classSchedulesRepo.existsBySchoolClassIdAndDayOfWeekAndPeriodNumberAndIdNot(schedule.getSchoolClass().getId(), schedule.getDayOfWeek(), schedule.getPeriodNumber(), schedule.getId())) {
            throw new ValidationException(ErrorCode.CLASS_ALREADY_HAS_TEACHER_ASSIGNED_AT_THIS_TIME);
        }

        Subject subject =
                subjectRepository.findById(subjectId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        schedule.setTeacher(teacher);
        schedule.setSubject(subject);

        return ClassScheduleMapper.toDto(classSchedulesRepo.save(schedule));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassScheduleDto> getByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

        if (student.getStudentSchoolClass() == null) {
            return List.of();
        }

        return getBySchoolClass(student.getStudentSchoolClass().getId());
    }
}