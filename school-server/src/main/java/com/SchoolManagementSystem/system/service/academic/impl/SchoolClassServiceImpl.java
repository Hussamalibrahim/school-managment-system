package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SchoolClassMapper;
import com.SchoolManagementSystem.system.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.system.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.system.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.service.academic.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.SchoolManagementSystem.system.config.ScheduleConstants.DEFAULT_PERIODS;


@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;
    private final ClassScheduleRepository classScheduleRepo;

    @Override
    @Transactional
    public SchoolClassDto update(Long id, SchoolClassDto dto) {

        SchoolClass clazz = schoolClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        SchoolClassMapper.updateEntity(clazz, dto);

        return SchoolClassMapper.toDto(
                schoolClassRepository.save(clazz));
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolClassDto getById(Long id) {
        return schoolClassRepository.findById(id)
                .map(SchoolClassMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolClassDto> getAll() {
        return schoolClassRepository.findAll()
                .stream()
                .map(SchoolClassMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        schoolClassRepository.delete(
                schoolClassRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND)));
    }

    @Override
    @Transactional
    public SchoolClassDto save(SchoolClassDto dto) {
        if (schoolClassRepository.existsByGradeLevelAndSection(dto.gradeLevel(), dto.section())) {
            throw new ValidationException(ErrorCode.CLASS_ALREADY_EXISTS);
        }
        SchoolClass schoolClass = schoolClassRepository.save(SchoolClassMapper.toEntity(dto));

        initClassSchedule(schoolClass);

        return SchoolClassMapper.toDto(schoolClass);
    }

    private void initClassSchedule(SchoolClass schoolClass) {

        for (DayOfWeek day : DayOfWeek.values()) {
            if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
                continue;
            }
            for (int i = 1; i <= DEFAULT_PERIODS; i++) {

                ClassSchedule schedule = new ClassSchedule();

                schedule.setSchoolClass(schoolClass);
                schedule.setDayOfWeek(day);
                schedule.setPeriodNumber(PeriodNumber.values()[i - 1]);

                classScheduleRepo.save(schedule);
            }
        }
    }

    @Override
    public List<SchoolClassDto> getBySchoolClassByTeacher(Long teacherId) {

        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }

        List<ClassSchedule> schedules =
                classScheduleRepo.findByTeacherId(teacherId);

        Set<SchoolClass> teacherClasses = schedules.stream()
                .map(ClassSchedule::getSchoolClass)
                .collect(Collectors.toSet());

        return teacherClasses.stream()
                .map(SchoolClassMapper::toDto)
                .toList();
    }
}