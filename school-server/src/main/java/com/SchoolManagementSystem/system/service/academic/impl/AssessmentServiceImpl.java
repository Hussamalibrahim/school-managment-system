package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.system.dto.academic.request.AssessmentCreateRequest;
import com.SchoolManagementSystem.system.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.AssessmentMapper;
import com.SchoolManagementSystem.system.entity.academic.Assessment;
import com.SchoolManagementSystem.system.repository.academic.AssessmentRepository;
import com.SchoolManagementSystem.system.repository.academic.ClassScheduleRepository;
import com.SchoolManagementSystem.system.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.system.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.AssessmentService;
import com.SchoolManagementSystem.system.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;

    private final SemesterService semesterService;

    @Override
    public AssessmentDto save(AssessmentDto dto) {

//        Subject subject = subjectRepository.findById(dto.subjectId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
//
//        Semester semester = semesterRepository.findById(dto.semesterId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));
//
//        Teacher teacher = teacherRepository.findById(dto.teacherId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));
//
//        Assessment assessment = AssessmentMapper.toEntity(dto);
//
//        assessment.setSubject(subject);
//        assessment.setSemester(semester);
//        assessment.setTeacher(teacher);
//
//        return AssessmentMapper.toDto(
//                assessmentRepository.save(assessment)
//        );
        return null;
    }

    @Override
    public AssessmentDto save(UserPrincipal user, AssessmentCreateRequest request) {

        ClassSchedule classSchedule = classScheduleRepository.findById(request.classScheduleId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_SCHEDULE_NOT_FOUND));
        Teacher scheduleTeacher = classSchedule.getTeacher();

        if (scheduleTeacher == null) {
            throw new ValidationException(ErrorCode.TEACHER_NOT_ASSIGNED_TO_CLASS);
        }

        if (!scheduleTeacher.getId().equals(user.getRefId())) {
            throw new ValidationException(ErrorCode.UNAUTHORIZED);
        }
        if (!teacherRepository.existsById(scheduleTeacher.getId())) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }

        Semester semester = semesterService.getCurrentSemester();

        Assessment assessment = new Assessment();

        AssessmentMapper.fromCreateRequest(assessment, request);

        assessment.setClassSchedule(classSchedule);
        assessment.setSemester(semester);


        assessment.setTeacher(scheduleTeacher);

        return AssessmentMapper.toDto(
                assessmentRepository.save(assessment)
        );
    }

    @Override
    public AssessmentDto update(Long id, AssessmentDto dto) {
        return null;
//        Assessment assessment = assessmentRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND));
//
//        Subject subject = subjectRepository.findById(dto.subjectId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
//
////        Semester semester = semesterRepository.findById(dto.semesterId())
////                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));
//
//        Semester semester = semesterService.getCurrentSemester();
//
//        Teacher teacher = teacherRepository.findById(dto.teacherId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));
//
//        AssessmentMapper.updateEntity(assessment, dto);
//
//        assessment.setSubject(subject);
//        assessment.setSemester(semester);
//        assessment.setTeacher(teacher);
//
//        return AssessmentMapper.toDto(
//                assessmentRepository.save(assessment)
//        );
    }

    @Override
    public AssessmentDto update(
            Long id,
            UserPrincipal user,
            AssessmentCreateRequest request) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND));

        ClassSchedule classSchedule = classScheduleRepository.findById(request.classScheduleId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_SCHEDULE_NOT_FOUND));

        Semester semester = semesterService.getCurrentSemester();

        AssessmentMapper.fromCreateRequest(assessment, request);

        assessment.setClassSchedule(classSchedule);
        assessment.setSemester(semester);

        return AssessmentMapper.toDto(
                assessmentRepository.save(assessment)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentDto getById(Long id) {

        return assessmentRepository.findById(id)
                .map(AssessmentMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getAll() {

        return assessmentRepository.findAll()
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND));

        assessmentRepository.delete(assessment);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getTeacherAssessments(Long teacherId) {

        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }

        return assessmentRepository
                .findByTeacherId(teacherId)
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getMyAssessments(UserPrincipal user) {

        return assessmentRepository
                .findByTeacherId(user.getRefId())
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getClassScheduleAssessments(
            Long classScheduleId,
            Long semesterId) {

        if (!classScheduleRepository.existsById(classScheduleId)) {
            throw new NotFoundException(ErrorCode.CLASS_SCHEDULE_NOT_FOUND);
        }

        semesterService.getById(semesterId);

        return assessmentRepository
                .findByClassScheduleIdAndSemesterId(classScheduleId, semesterId)
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getSchoolClassAssessments(
            Long schoolClassId,
            Long semesterId) {

        if (!schoolClassRepository.existsById(schoolClassId)) {
            throw new NotFoundException(ErrorCode.CLASS_NOT_FOUND);
        }

        semesterService.getById(semesterId);

        return assessmentRepository
                .findByClassScheduleSchoolClassIdAndSemesterId(
                        schoolClassId,
                        semesterId)
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getSubjectAssessments(Long subjectId) {

        if (!subjectRepository.existsById(subjectId)) {
            throw new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }


        return assessmentRepository
                .findByClassScheduleSubjectId(
                        subjectId)
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> getTeacherSubjectAssessments(
            Long teacherId) {

        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }

        return assessmentRepository
                .findByClassScheduleTeacherId(
                        teacherId)
                .stream()
                .map(AssessmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AssessmentDto> getTeacherAssessments(UserPrincipal user) {
        getTeacherAssessments(user.getRefId());
        return List.of();
    }
}