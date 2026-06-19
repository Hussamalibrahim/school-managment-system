package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.mapper.academic.SchoolClassMapper;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.System.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.service.academic.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.SchoolManagementSystem.System.config.ScheduleConstants.DEFAULT_PERIODS;


@Service
@RequiredArgsConstructor
@Transactional
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final ClassScheduleRepository classScheduleRepo;
    private final SchoolClassRepository schoolRepository;

    @Override
    public SchoolClassDto update(Long id, SchoolClassDto dto) {
        SchoolClass clazz = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        clazz.setGradeLevel(dto.gradeLevel());
        clazz.setSection(dto.section());
        clazz.setLocation(dto.location());
        clazz.setCapacity(dto.capacity());

        clazz = schoolClassRepository.save(clazz);
        return SchoolClassMapper.toDto(clazz);
    }

    @Override
    public SchoolClassDto getById(Long id) {
        return schoolClassRepository.findById(id)
                .map(SchoolClassMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Class not found"));
    }

    @Override
    public List<SchoolClassDto> getAll() {
        return schoolClassRepository.findAll()
                .stream()
                .map(SchoolClassMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        schoolClassRepository.deleteById(id);
    }


    @Override
    public List<StudentDto> getStudentsById(Long id) {
        return List.of();
    }
    @Override
    public SchoolClassDto save(SchoolClassDto dto)
    {

        SchoolClass schoolClass = SchoolClassMapper.toEntity(dto);


        schoolClass = schoolClassRepository.save(schoolClass);

        initClassSchedule(schoolClass.getId());

        return SchoolClassMapper.toDto(schoolClass);
    }
    private void initClassSchedule(Long classId)
    {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        for (int i = 1; i <= DEFAULT_PERIODS; i++)
        {
            ClassSchedule schedule = new ClassSchedule();

            schedule.setSchoolClass(schoolClass);
            schedule.setPeriodNumber(PeriodNumber.values()[i - 1]);

            classScheduleRepo.save(schedule);
        }
    }
}