package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
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
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    @Override
    public List<ClassScheduleDto> getAll() {
        return classSchedulesRepo.findAll().
                stream().map(ClassScheduleMapper::toDto).toList();
    }

    @Override
    public List<ClassScheduleDto> getByTeacher(Long teacherId) {
        return classSchedulesRepo.findByTeacherId(teacherId)
                .stream()
                .map(ClassScheduleMapper::toDto).toList();
    }

    @Override
    public List<ClassScheduleDto> getByClass(Long classId) {
        return classSchedulesRepo.findStudentBySchoolClassId(classId)
                .stream()
                .map(ClassScheduleMapper::toDto).toList();
    }
    @Override
    public List<StudentDto> getStudentsByTeacher(Long teacherId) {

        List<ClassSchedule> schedules =
                classSchedulesRepo.findByTeacherId(teacherId);

        Set<Long> classIds = schedules.stream()
                .map(s -> s.getSchoolClass().getId())
                .collect(Collectors.toSet());

        List<Student> students =
                studentRepository.findByStudentSchoolClassIdIn((List<Long>) classIds);

        return students.stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    public ClassScheduleDto addExtraPeriod(Long classId) {

        long count = classSchedulesRepo.countBySchoolClassId(classId);

        if (count >= MAX_PERIODS) {
            throw new RuntimeException("Max 7 periods allowed");
        }

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        ClassSchedule schedule = new ClassSchedule();
        schedule.setSchoolClass(schoolClass);

        schedule.setPeriodNumber(
                PeriodNumber.values()[(int) count]
        );

        return ClassScheduleMapper.toDto(classSchedulesRepo.save(schedule));
    }


    @Override
    public ClassScheduleDto assignTeacher(Long scheduleId, Long teacherId, Long subjectId) {

        ClassSchedule schedule =
                classSchedulesRepo.findById(scheduleId)
                        .orElseThrow();

        Teacher teacher =
                teacherRepository.findById(teacherId)
                        .orElseThrow();

        Subject subject =
                subjectRepository.findById(subjectId)
                        .orElseThrow();

        schedule.setTeacher(teacher);
        schedule.setSubject(subject);

        return ClassScheduleMapper.toDto(classSchedulesRepo.save(schedule));
    }
}